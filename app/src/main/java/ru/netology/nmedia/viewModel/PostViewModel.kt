package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.SingleLiveEvent
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepositoryImpl
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    shares = 0,
    views = 0,
    video = "0"
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    /*private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(context = application).postDao()
    )*/
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

   /* fun loadPosts() {
        thread {
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }*/

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun save() {
        /*edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
        }
        edited.value = empty*/

        edited.value?.let{

            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    //_data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })
        }
    }




    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
       /* thread {// repository.likeById(id)
            //TODO("NOT YET IMPLEMENTED")
            val likedPost = repository.likeById(id)
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .map { if (it.id == id) likedPost else it }
                )
            )
        }*/
        repository.likeAsync(id, object : PostRepository.Callback<Post>{
            override fun onSuccess(post: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map { if (it.id == id) post else it }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        }
        )

    }

    fun unlikeById(id: Long) {
        /*thread { //repository.unlikeById(id)
            //TODO("NOT YET IMPLEMENTED")
            val unlikedPost = repository.unlikeById(id)
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .map { if (it.id == id) unlikedPost else it }
                )
            )
        }*/
        repository.likeAsync(id, object : PostRepository.Callback<Post>{
            override fun onSuccess(post: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map { if (it.id == id) post else it }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        }
        )


    }

    fun removeById(id: Long) {
        /*thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                //TODO("NOT YET IMPLEMENTED")
                //repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }*/
        val old = _data.value?.posts.orEmpty()
        _data.postValue(
            _data.value?.copy(posts = _data.value?.posts.orEmpty()
                .filter { it.id != id }
            )
        )

        repository.removeAsync(id, object: PostRepository.Callback<Post> {
            override fun onSuccess(post: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map { if (it.id == id) post else it }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
        )

    }

    fun repostById(id: Long)// = repository.repostById(id)
    {
        /*thread {
            //TODO("NOT YET IMPLEMENTED")
            //val repostPost = repository.repostById(id)
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    //.map { if (it.id == id) repostPost else it }
                )
            )
        }*/

        repository.repostAsync(id, object: PostRepository.Callback<Post>{
            override fun onSuccess(post: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map { if (it.id == id) post else it }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        }
        )
    }
}
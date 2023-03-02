package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post

/*interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun unlikeById(id: Long): Post
    fun save(post: Post)
    fun removeById(id: Long)
    fun repostById(id: Long): Post

}*/


/*interface PostRepository {
    fun getAllAsync(callback: Callback<List<Post>>)
    fun likeById(id: Long, callback: Callback<Post>)
    fun unlikeById(id: Long, callback: Callback<Post>)
    fun save(post: Post, callback: Callback<Post>)
    fun removeById(id: Long, callback: Callback<Unit>)
    fun repostById(id: Long, callback: Callback<Post>)

    interface Callback <T> {
        fun onSuccess(posts: T) {}
        fun onError(e: Exception) {}
    }
}*/

interface PostRepository {
    //TODO
    val data: Flow<List<Post>>
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun showAll()
    //suspend fun isEmpty(): Boolean

   /* suspend fun showOnlyVisible()*/
    suspend fun getAll()
    suspend fun save(post: Post)
    suspend fun removeById(id: Long)
    suspend fun likeById(id: Long)
    suspend fun unlikeById(id: Long)
    suspend fun repostById(id: Long)
}



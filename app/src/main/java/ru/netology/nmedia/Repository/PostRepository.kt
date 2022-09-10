package ru.netology.nmedia.Repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)

}
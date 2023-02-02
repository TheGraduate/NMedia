package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

/*interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun unlikeById(id: Long): Post
    fun save(post: Post)
    fun removeById(id: Long)
    fun repostById(id: Long): Post

}*/


interface PostRepository {

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
}



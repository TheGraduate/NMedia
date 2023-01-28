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
    fun save(post: Post, callback: Callback<Post>)
    fun unlikeAsync(id: Long, callback: Callback<Post>)
    fun repostAsync(id: Long, callback: Callback<Post>)

    fun getAllAsync(callback: Callback<List<Post>>)
    fun likeAsync(id: Long, callback: Callback<Post>)
    fun removeAsync(id: Long, callback: Callback<Unit>)


    interface Callback <T> {
        fun onSuccess(posts: T) {}
        fun onError(e: Exception) {}
    }
}



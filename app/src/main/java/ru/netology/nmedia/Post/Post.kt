package ru.netology.nmedia.Post

data class Post (
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var likedByMe: Boolean,
        var likes: Int,
        var shares: Int,
        var views: Int,
        var video: String? = "0"

)
package ru.netology.nmedia

data class Post (
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var likedByMe: Boolean,
        var likes: Int,
        var shares: Int,
        var views: Int
)
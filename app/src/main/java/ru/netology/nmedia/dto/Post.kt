package ru.netology.nmedia.dto

data class Post (
        val id: Long,
        val author: String,
        val authorAvatar: String,
        val content: String,
        val published: String,
        var likedByMe: Boolean,
        var likes: Int,
        var shares: Int,
        var views: Int,
        var video: String = "0",
        var hidden: Boolean = false
                //TODO
)
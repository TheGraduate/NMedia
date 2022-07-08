package ru.netology.nmedia

import android.provider.MediaStore
import java.net.URL

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
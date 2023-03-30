package ru.netology.nmedia.dto

import ru.netology.nmedia.enumeration.AttachmentType

data class Post(
        val id: Long,
        val author: String,
        val authorId: Long,
        val authorAvatar: String,
        val content: String,
        val published: String,
        var likedByMe: Boolean,
        var likes: Int,
        var shares: Int,
        var views: Int,
        var video: String = "0",
        var hidden: Boolean = false,
        val attachment: Attachment? = null,
        val ownedByMe: Boolean = false,
)

data class Attachment(
        val url: String,
        val type: AttachmentType,
)
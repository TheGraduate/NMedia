package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post

interface PostRepository {
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

    suspend fun saveWithAttachment(post: Post, upload: MediaUpload)

    suspend fun upload(upload: MediaUpload): Media

}



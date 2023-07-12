package ru.netology.nmedia.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: Flow<PagingData<Post>>
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun showAll()
    suspend fun getAll()
    suspend fun save(post: Post, upload: MediaUpload?)
    suspend fun removeById(id: Long)
    suspend fun likeById(id: Long)
    suspend fun unlikeById(id: Long)
    suspend fun repostById(id: Long)
    suspend fun upload(upload: MediaUpload): Media
    suspend fun updateUser(login: String, pass: String): Response<ResponseBody>

}



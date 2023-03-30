package ru.netology.nmedia.viewModel

import android.app.Application
import android.media.session.MediaSession
import android.util.JsonToken
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class RegistrationViewModel(application: Application) : AndroidViewModel(application) { // todo

    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    fun updateUser(login: String, pass: String) {
        viewModelScope.launch {
            try {
                val token = repository.updateUser(login, pass)
                val appAuth = AppAuth.getInstance()
                appAuth.setAuth()
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}
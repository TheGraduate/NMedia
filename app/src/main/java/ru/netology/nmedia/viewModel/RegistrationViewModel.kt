package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class RegistrationViewModel(application: Application) : AndroidViewModel(application) { // todo

    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    fun updateUser(login: String, pass: Long) {
        viewModelScope.launch {
            try {
                val gson = Gson()
                val jsonObject = gson.fromJson(repository.updateUser(login, pass).body()?.string(), JsonObject::class.java)
                val id = jsonObject.get("id").asLong
                val token = jsonObject.get("token").asString
                AppAuth.getInstance().setAuth(id,token)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}
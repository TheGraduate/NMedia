package ru.netology.nmedia.viewModel

import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.PostRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: PostRepository,
    private val appAuth: AppAuth,
) : ViewModel() {

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    fun updateUser(login: String, pass: String) {
        viewModelScope.launch {
            try {
                val jsonObject = Gson().fromJson(repository.updateUser(login, pass).body()?.string(), JsonObject::class.java)
                appAuth.setAuth(jsonObject.get("id").asLong, jsonObject.get("token").asString)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}
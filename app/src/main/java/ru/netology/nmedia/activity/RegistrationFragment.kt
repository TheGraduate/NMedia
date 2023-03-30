package ru.netology.nmedia.activity

import android.media.session.MediaSession
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import org.json.JSONObject
import retrofit2.Response
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth


import ru.netology.nmedia.databinding.RegistrationFragmentBinding
import ru.netology.nmedia.viewModel.RegistrationViewModel

class RegistrationFragment : Fragment() { // todo

    private lateinit var login: EditText
    private lateinit var password: EditText
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = RegistrationFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        val view = inflater.inflate(R.layout.registration_fragment, container, false)

        login = view.findViewById(R.id.login)
        password = view.findViewById(R.id.password)

        binding.enterButton.setOnClickListener {

            val login = login.text.toString()
            val password = password.text.toString()

            try {
                // Вызываем функцию updateUser в viewModel, чтобы получить токен
                val token = viewModel.updateUser(login, password)

                // Сохраняем id и token в AppAuth
                val appAuth = AppAuth.getInstance()
                val authState = appAuth.authStateFlow.value

                if (authState.token != null) {
                    val id = authState.id
                    val token = authState.token
                }


                // Навигируем обратно к предыдущему фрагменту
                findNavController().navigateUp()
            } catch (e: Exception) {
                // Обрабатываем ошибку
            }
        }



        return view
    }
}
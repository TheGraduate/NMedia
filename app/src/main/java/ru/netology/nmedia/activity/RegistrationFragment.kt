package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.databinding.RegistrationFragmentBinding
import ru.netology.nmedia.viewModel.RegistrationViewModel

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private val viewModel: RegistrationViewModel by activityViewModels()

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

        binding.enterButton.setOnClickListener {
            val loginStr = binding.login.text.toString()
            val passwordStr = binding.password.text.toString()
            viewModel.updateUser(loginStr, passwordStr)
            findNavController().navigateUp()
        }
        return binding.root
    }
}
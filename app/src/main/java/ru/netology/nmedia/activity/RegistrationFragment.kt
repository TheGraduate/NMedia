package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
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
            val loginStr = login.text.toString()
            val passwordStr = password.text.toString()
            viewModel.updateUser(loginStr, passwordStr.toLong())
            //findNavController().popBackStack()
            //findNavController().navigateUp()
            findNavController().navigate(R.id.action_registrationFragment_to_feedFragment)
        }
        return view
    }
}
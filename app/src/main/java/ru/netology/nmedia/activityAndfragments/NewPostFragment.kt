package ru.netology.nmedia.activityAndfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Utils.AndroidUtils
import ru.netology.nmedia.Utils.StringArg
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {

        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()

            }
        return binding.root
    }
}
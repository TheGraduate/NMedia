package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentEditPostBinding

class EditPostFragment: Fragment() {

   /* companion object {
        var Bundle.textArg: String? by StringArg
    }*/

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        val arg1Value = requireArguments().getString(Intent.EXTRA_TEXT)
        binding.edit.setText(arg1Value)

        binding.ok.setOnClickListener {

            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        return binding.root
    }
}
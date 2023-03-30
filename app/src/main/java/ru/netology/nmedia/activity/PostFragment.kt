package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentPostBinding

class PostFragment : Fragment() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val args by navArgs<PostFragmentArgs>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)


        val viewHolder = PostViewHolder(binding.cardPost, object : OnInteractionListener {


            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }


                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onPlay(post: Post) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(browserIntent)
            }

        })

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.posts.find { it.id == args.postId.toLong() } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }

            val sendPostText = Bundle()
            sendPostText.putString(Intent.EXTRA_TEXT, it.content)

            findNavController().navigate(R.id.action_postFragment_to_editPostFragment, sendPostText)
        }

        return binding.root
    }
}

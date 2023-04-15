package ru.netology.nmedia.activity

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment() {

    @Inject
    lateinit var auth: AppAuth
    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: PostViewModel by activityViewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

    ) : View {
        val fragmentManager = childFragmentManager
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostsAdapter(object : OnInteractionListener {

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                if (auth.authStateFlow.value.id != 0L
                    && auth.authStateFlow.value.token != null) {
                    if (post.likedByMe) {
                        viewModel.unlikeById(post.id)
                    } else {
                        viewModel.likeById(post.id)
                    }
                } else {
                   val dialogFragment = AuthSuggestionFragment()
                    dialogFragment.show(fragmentManager, "my_suggestion_fragment_tag")
                }
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.repostById(post.id)
            }

            override fun onPlay(post: Post) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(browserIntent)
            }

            override fun onPost(post: Post) {
                val action =
                    FeedFragmentDirections.actionFeedFragmentToPostFragment(post.id.toInt())
                findNavController().navigate(action)
            }

            override fun onImage(post: Post) {
                val sendPostText = Bundle()
                sendPostText.putString(EXTRA_TEXT, post.attachment?.url)
                findNavController().navigate(
                    R.id.action_feedFragment_to_postImageFragment,
                    sendPostText
                )
            }

        })

        binding.list.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        })

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }


        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            binding.showPosts.isVisible = state > 0
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshPosts()
        }

        binding.fab.setOnClickListener {
            if (auth.authStateFlow.value.id != 0L
                && auth.authStateFlow.value.token != null) {
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
            } else {
                val dialogFragment = AuthSuggestionFragment()
                dialogFragment.show(fragmentManager, "my_suggest_fragment_tag")
            }

        }

        binding.showPosts.setOnClickListener {
            viewModel.showAll()
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }

            val sendPostText = Bundle()
            sendPostText.putString(EXTRA_TEXT, it.content)

            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, sendPostText)
        }

        return binding.root
    }
}
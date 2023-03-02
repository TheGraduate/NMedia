package ru.netology.nmedia.activity

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.emptyFlow
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.R
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    //private val viewModel: PostViewModel by activityViewModels()
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) : View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false

        )

        val adapter = PostsAdapter (object : OnInteractionListener {

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                if (post.likedByMe) {
                    viewModel.unlikeById(post.id)
                } else {
                    viewModel.likeById(post.id)
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
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.repostById(post.id)
            }

            override fun onPlay(post: Post) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(browserIntent)
            }

            override fun onPost(post: Post) {
                val action = FeedFragmentDirections.actionFeedFragmentToPostFragment(post.id.toInt())
                findNavController().navigate(action)
            }


        })

       /* if(viewModel.isEmpty()){
            binding.showPosts.visibility = View.GONE
        } else{
            binding.showPosts.visibility = View.VISIBLE
        }
*/


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

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0){
                    binding.list.smoothScrollToPosition(0)
                }
            }
        })

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }


        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            println(state)
            binding.showPosts.visibility = View.VISIBLE
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshPosts()
        }


        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        binding.showPosts.setOnClickListener {
            viewModel.showAll()
            binding.showPosts.visibility = View.GONE
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
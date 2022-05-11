package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

import android.widget.PopupMenu

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    //private val onLikeListener: OnLikeListener,
    //private val onShareListener: OnShareListener
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

           // likeCount?.text = calculateParametrs(post.likes)
            //shareCount?.text = calculateParametrs(post.shares)


            like.isChecked = post.likedByMe
            like.text = "${post.likes}"
            share.text = "${post.shares}"

            like.text = calculateParametrs(post.likes)
            share.text = calculateParametrs(post.shares)
            viewCount?.text = calculateParametrs(post.views)

            /*if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_baseline_favorited_24)
            } else {
                like.setImageResource(R.drawable.ic_baseline_favorite_24)
            }*/

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            like?.setOnClickListener {
                //onLikeListener(post)
                onInteractionListener.onLike(post)
            }
            share?.setOnClickListener {
                //onShareListener(post)
                onInteractionListener.onShare(post)
            }

        }
    }
}
    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

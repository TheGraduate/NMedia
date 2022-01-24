package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter(private val onLikeListener: OnLikeListener) : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content


            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_baseline_favorited_24)
            }

            likeCount?.text = calculateParametrs(post.likes)
            shareCount?.text = calculateParametrs(post.shares)
            viewCount?.text = calculateParametrs(post.views)

            like.setOnClickListener{
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_baseline_favorited_24
                    } else {
                        R.drawable.ic_baseline_favorite_24
                    }
                )
                if (post.likedByMe) {
                    post.likes++
                } else {
                    post.likes--
                }
                likeCount?.text = calculateParametrs(post.likes)
            }
            share?.setOnClickListener {
                post.shares++
                shareCount?.text = calculateParametrs(post.shares)
            }
        }
    }
}
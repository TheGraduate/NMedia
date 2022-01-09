package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content

                like.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_baseline_favorited_24
                    } else {
                        R.drawable.ic_baseline_favorite_24
                      }
                    )
                if (post.likedByMe) {
                    like?.setImageResource(R.drawable.ic_baseline_favorited_24)
                }

                likeCount?.text = calculateParametrs(post.likes)
                shareCount?.text = calculateParametrs(post.shares)
                viewCount?.text = calculateParametrs(post.views)

                like?.setOnClickListener {
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
}
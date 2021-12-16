package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "17 мая в 12:12",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_baseline_favorited_24)
            }

            likeCount?.text = calculateParametrs(post.likes)
            shareCount?.text = calculateParametrs(post.shares)
            viewCount?.text = calculateParametrs(post.views)

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorited_24 else R.drawable.ic_baseline_favorite_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                       likeCount?.text = calculateParametrs(post.likes)
            }

            share?.setOnClickListener {
                post.shares++
                shareCount?.text = calculateParametrs(post.shares)
            }

        }
    }
}
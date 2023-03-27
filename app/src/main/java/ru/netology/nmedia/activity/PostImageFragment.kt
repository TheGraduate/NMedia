package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.netology.nmedia.R

class PostImageFragment: Fragment() {

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val arg1Value = requireArguments().getString(Intent.EXTRA_TEXT)
        val view = inflater.inflate(R.layout.post_image_fragment, container, false)
        imageView = view.findViewById(R.id.postImage)
        arguments?.let {
            Glide.with(this)
                .load("http://10.0.2.2:9999/media/$arg1Value")
                .into(imageView)
        }
        return view
    }
}
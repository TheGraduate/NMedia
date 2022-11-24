package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random

    class FCMService : FirebaseMessagingService() {
        private val action = "action"
        private val content = "content"
        private val channelId = "remote"
        private val gson = Gson()

        override fun onCreate() {
            super.onCreate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.channel_remote_name)
                val descriptionText = getString(R.string.channel_remote_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        override fun onMessageReceived(message: RemoteMessage) {

            print(Gson().toJson(message))
            try {
                message.data[action]?.let {
                    when (Action.valueOf(it)) {
                        Action.LIKE -> handleLike(gson.fromJson(message.data[content], Like::class.java))
                        Action.SHARE -> handleShare(gson.fromJson(message.data[content], Share::class.java))
                        Action.NEW_POST -> handleNewPost(gson.fromJson(message.data[content], NewPost::class.java))
                    }
                }
            } catch (e : IllegalArgumentException) {
                println(e.message)
            }
        }

        override fun onNewToken(token: String) {
            println(token)
        }

        private fun handleLike(content: Like) {
            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(
                    getString(
                        R.string.notification_user_liked,
                        content.userName,
                        content.postAuthor,
                    )
                )
                .setContentText(content.content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(this)
                .notify(Random.nextInt(100_000), notification)
        }

        private fun handleShare(content: Share) {
            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(
                    getString(
                        R.string.notification_user_share,
                        content.userName,
                        content.postAuthor,
                    )
                )
                .setContentText(content.content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(this)
                .notify(Random.nextInt(100_000), notification)
        }

        private fun handleNewPost(content: NewPost) {
            val bigTextForNotification = "ДАННОЕ СООБЩЕНИЕ (МАТЕРИАЛ) СОЗДАНО И (ИЛИ) " +
                    "РАСПРОСТРАНЕНО ИНОСТРАННЫМ СРЕДСТВОМ МАССОВОЙ ИНФОРМАЦИИ, " +
                    "ВЫПОЛНЯЮЩИМ ФУНКЦИИ ИНОСТРАННОГО АГЕНТА"
            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(
                    getString(
                        R.string.notification_user_newpost,
                        content.userName
                    )
                )
                .setContentText(bigTextForNotification)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(bigTextForNotification))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(this)
                .notify(Random.nextInt(100_000), notification)
        }
    }

    enum class Action {
        LIKE, SHARE, NEW_POST
    }

    data class Like(
        val userId: Long,
        val userName: String,
        val postId: Long,
        val postAuthor: String,
        val content: String,
    )

    data class Share(
        val userId: Long,
        val userName: String,
        val postId: Long,
        val postAuthor: String,
        val content: String,
    )

    data class NewPost(
        val userId: Long,
        val userName: String,
        val postId: Long,
        val postAuthor: String,
        val content: String,
    )

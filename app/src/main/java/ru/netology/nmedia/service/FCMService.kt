package ru.netology.nmedia.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.PermissionChecker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
        private val action = "action"
        private val content = "content"
        private val channelId = "remote"
        private val gson = Gson()
        @Inject
        lateinit var auth: AppAuth

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
            val messageNotification = gson.fromJson(message.data[content], ru.netology.nmedia.dao.Notification::class.java)

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            when {
                messageNotification.recipientId == auth.authStateFlow.value.id.toString() -> {
                    notificationBuilder.setContentTitle("ID: ${messageNotification.recipientId}")
                        .setContentText("Content: ${messageNotification.content}")
                }

                messageNotification.recipientId == "0" &&
                        messageNotification.recipientId != auth.authStateFlow.value.id.toString() -> {
                    auth.authStateFlow.value.token?.let { onNewToken(it) }
                }

                messageNotification.recipientId != "0" &&
                        messageNotification.recipientId != auth.authStateFlow.value.id.toString() -> {
                    auth.authStateFlow.value.token?.let { onNewToken(it) }
                }

              /*  messageNotification.recipientId == null -> {
                    notificationBuilder.setContentTitle("ID: ${messageNotification.recipientId}")
                        .setContentText("Content: ${messageNotification.content}")
                }*/
            }

            val notification = notificationBuilder.build()

            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(this)
                    .notify(Random.nextInt(100_000), notification)
            }

       /*     message.data[action]?.let {
                when (Action.valueOf(it)) {
                    Action.LIKE -> handleLike(gson.fromJson(message.data[content], Like::class.java))
                    Action.SHARE -> handleShare(gson.fromJson(message.data[content], Share::class.java))
                    Action.NEW_POST -> handleNewPost(gson.fromJson(message.data[content], NewPost::class.java))
                }
            }*/
        }


        override fun onNewToken(token: String) {
            //println(token)
            auth.sendPushToken(token)
        }

       /* private fun handleLike(content: Like) {
            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(
                    getString(
                        R.string.notification_user_liked,
                        content.userName,
                        content.postAuthor,
                    )
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(this)
                    .notify(Random.nextInt(100_000), notification)
            }
        }*/

       /* private fun handleShare(content: Share) {
            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(
                    getString(
                        R.string.notification_user_share,
                        content.userName,
                        content.postAuthor,
                    )
                )
                //.setContentText(content.content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
            NotificationManagerCompat.from(this)
                .notify(Random.nextInt(100_000), notification)
            }
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
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(bigTextForNotification)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(this)
                    .notify(Random.nextInt(100_000), notification)
            }
        }*/
    }

   /* enum class Action {
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
    )*/

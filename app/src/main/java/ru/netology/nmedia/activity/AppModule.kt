package ru.netology.nmedia.activity

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }

    @Provides
    fun provideGoogleApiAvailability(activity: Activity): GoogleApiAvailability {
        return GoogleApiAvailability.getInstance()
    }
}


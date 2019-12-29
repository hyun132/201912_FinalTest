package com.example.a201912_finaltest.fcm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

//        Notification 타입의 푸시에 사용되는 자료
//        remoteMessage?.

//        데이터메세지에 대한 자료
//        remoteMessage?.data
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d("발급받은 토큰 : " + token!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        FCM
    }
}

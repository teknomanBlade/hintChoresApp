package com.example.myapplication.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import com.example.myapplication.R
import com.example.myapplication.domain.model.NotificationSound
import androidx.core.net.toUri

class NotificationChannelManager(
    private val context: Context
) {

    fun createChannels() {
        createChannel(NotificationSound.Default, R.raw.notification_default)
        createChannel(NotificationSound.Soft, R.raw.notification_soft)
        createChannel(NotificationSound.Alarm, R.raw.notification_alert)
        createChannel(NotificationSound.Silent, null, silent = true)
    }

    private fun createChannel(
        sound: NotificationSound,
        soundRes: Int?,
        silent: Boolean = false
    ) {

        val manager = context.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            sound.channelId,
            "Recordatorios",
            NotificationManager.IMPORTANCE_HIGH
        )

        if (silent) {
            channel.setSound(null, null)
        } else if (soundRes != null) {
            val uri = "android.resource://${context.packageName}/$soundRes".toUri()

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            channel.setSound(uri, audioAttributes)
        }

        manager.createNotificationChannel(channel)
    }
}
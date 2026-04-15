package com.example.myapplication.model.data.provider

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.myapplication.R

class NotificationProvider {
    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            "reminder_channel",
            "Recordatorios",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showReminder(context: Context, message:String ,imagePath: String?) {

        val builder = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Recordatorio")
            .setContentText("$message 😉")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        imagePath?.let {
            val bitmap = BitmapFactory.decodeFile(it)
            builder.setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(bitmap)
            )
        }

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}
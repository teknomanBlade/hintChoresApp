package com.example.myapplication.model.data.provider

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
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
            .setSmallIcon(R.drawable.ic_push_notification_hint)
            .setContentTitle("Recordatorio")
            .setContentText("$message 😉")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        imagePath?.let { path ->
            var bitmap = BitmapFactory.decodeFile(path)
            // Corregir rotación basada en metadatos EXIF
            val exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            bitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                else -> bitmap
            }
            builder.setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(bitmap)
            )
        }

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }
}
package com.example.myapplication.model.data

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.myapplication.domain.extensions.toMillis
import com.example.myapplication.domain.model.NotificationSound
import com.example.myapplication.domain.model.ReminderDelay
import java.util.concurrent.TimeUnit

class ReminderScheduler(private val context: Context, private val prefs: ReminderPreferences) {

    fun scheduleReminder(message: String, imagePath: String?, reminderDelay: ReminderDelay,
                         sound: NotificationSound){
        val delayMillis = reminderDelay.toMillis()
        val data = workDataOf(
            ReminderWorker.KEY_MESSAGE to message,
            ReminderWorker.KEY_IMAGE_PATH to imagePath,
            ReminderWorker.KEY_CHANNEL_ID to sound.channelId
        )

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
    fun scheduleReminder(message: String, imagePath: String?, reminderDelay: ReminderDelay){
        val delayMillis = reminderDelay.toMillis()

        val data = Data.Builder()
            .putString("imagePath", imagePath)
            .putString("message",message)
            .build()

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
    fun scheduleReminder(message:String, imagePath: String? = null) {
        val minutes = prefs.getDelay()
        val delayMillis = minutes * 60 * 1000L

        val data = Data.Builder()
            .putString("imagePath", imagePath)
            .putString("message",message)
            .build()

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
}
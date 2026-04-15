package com.example.myapplication.model.data

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ReminderScheduler(private val context: Context, private val prefs: ReminderPreferences) {

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
package com.example.myapplication.model.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.model.data.provider.NotificationProvider

class ReminderWorker(
    context: Context,
    params: WorkerParameters,
    private val notificationProvider: NotificationProvider
) : Worker(context, params) {

    override fun doWork(): Result {
        val imagePath = inputData.getString("imagePath")
        val message = inputData.getString("message")
        notificationProvider.showReminder(applicationContext, message!!,imagePath)
        return Result.success()
    }
}
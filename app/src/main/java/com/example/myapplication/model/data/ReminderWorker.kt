package com.example.myapplication.model.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.myapplication.domain.model.NotificationSound
import com.example.myapplication.model.data.provider.NotificationProvider

class ReminderWorker(
    context: Context,
    params: WorkerParameters,
    private val notificationProvider: NotificationProvider
) : Worker(context, params) {


    override fun doWork(): Result {
        val imagePath = inputData.getString(KEY_IMAGE_PATH)
        val message = inputData.getString(KEY_MESSAGE)
        val channelId = inputData.getString(KEY_CHANNEL_ID)
            ?: NotificationSound.Default.channelId
        notificationProvider.showReminder(applicationContext, message!!,imagePath, channelId)
        return Result.success()
    }

    companion object {
        const val KEY_MESSAGE = "key_message"
        const val KEY_IMAGE_PATH = "key_image_path"
        const val KEY_CHANNEL_ID = "key_channel_id"
    }
}
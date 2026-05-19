package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.NotificationSound
import com.example.myapplication.domain.model.ReminderDelay
import com.example.myapplication.model.data.repository.ReminderRepository

class CreateReminderWithSFXUseCase(private val repository: ReminderRepository) {
    operator fun invoke(message:String, imagePath: String? = null, reminderDelay: ReminderDelay, sound: NotificationSound){
        repository.scheduleReminder(message,imagePath,reminderDelay,sound)
    }
}
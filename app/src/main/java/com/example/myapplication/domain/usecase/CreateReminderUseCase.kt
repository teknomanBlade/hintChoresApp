package com.example.myapplication.domain.usecase

import com.example.myapplication.model.data.repository.ReminderRepository

class CreateReminderUseCase(private val repository: ReminderRepository) {
    operator fun invoke(message:String, imagePath: String? = null) {
        repository.scheduleReminder(message,imagePath)
    }
}
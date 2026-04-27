package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.ReminderDelay
import com.example.myapplication.model.data.repository.ReminderRepository

class CreateReminderWithTimePickerUseCase(private val repository: ReminderRepository) {
    operator fun invoke(message:String, imagePath: String? = null, reminderDelay: ReminderDelay){
        repository.scheduleReminder(message,imagePath,reminderDelay)
    }
}
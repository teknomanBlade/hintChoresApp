package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository
import com.example.myapplication.model.data.entities.ReminderMessage

class GetSelectedMessageUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke(): ReminderMessage? {
        return repo.getSelectedMessage()
    }
}
package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository

class UpdateMessageUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke(id: Long) = repo.updateMessage(id)
}
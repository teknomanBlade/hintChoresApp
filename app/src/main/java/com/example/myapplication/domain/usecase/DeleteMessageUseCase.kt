package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository

class DeleteMessageUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke(id: Long) = repo.deleteMessage(id)
}
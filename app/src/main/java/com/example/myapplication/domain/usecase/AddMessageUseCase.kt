package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository

class AddMessageUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke(text: String) = repo.addMessage(text)
}
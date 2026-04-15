package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository

class GetMessagesUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke() = repo.getMessages()
}
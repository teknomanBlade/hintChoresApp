package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository

class DeleteSelectedMessageUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke() {
        repo.deleteSelectedMessage()
    }
}
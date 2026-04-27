package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository

class SaveSelectedMessageUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke(text: String) {
        repo.saveSelectedMessage(text)
    }
}



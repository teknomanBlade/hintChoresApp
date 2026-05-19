package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.messages.IMessagesRepository
import kotlinx.coroutines.flow.first

class GetSelectedSoundUseCase(private val repo: IMessagesRepository) {
    suspend operator fun invoke() : String? {
        return repo.getSelectedSound().first()
    }
}
package com.example.myapplication.domain.messages

import com.example.myapplication.model.data.entities.ReminderMessage

interface IMessagesRepository {
    suspend fun getMessages(): List<ReminderMessage>
    suspend fun addMessage(text: String)
    suspend fun deleteMessage(id: Long)
}
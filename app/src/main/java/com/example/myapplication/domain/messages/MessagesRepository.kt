package com.example.myapplication.domain.messages

import com.example.myapplication.core.messages.MessagesLocalDataSource
import com.example.myapplication.model.data.entities.ReminderMessage
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MessagesRepository (private val local: MessagesLocalDataSource
) : IMessagesRepository {
    private val mutex = Mutex()

    override suspend fun getMessages(): List<ReminderMessage> =
        local.getMessages()

    override suspend fun addMessage(text: String) = mutex.withLock {
        val current = local.getMessages().toMutableList()
        current.add(ReminderMessage(System.currentTimeMillis(), text))
        local.saveMessages(current)
    }

    override suspend fun deleteMessage(id: Long) = mutex.withLock {
        val updated = local.getMessages().filterNot { it.id == id }
        local.saveMessages(updated)
    }
}
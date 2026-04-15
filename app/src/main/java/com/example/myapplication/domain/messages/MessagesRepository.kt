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

    override suspend fun updateMessage(id: Long) = mutex.withLock {
        // 1. Obtenemos la lista actual
        val currentMessages = local.getMessages()

        // 2. Mapeamos la lista: si el ID coincide, invertimos el favorito; si no, dejamos el mensaje igual
        val updatedList = currentMessages.map { message ->
            if (message.id == id) {
                message.copy(isFavorite = !message.isFavorite) // Usamos copy para cambiar solo esa propiedad
            } else {
                message // Mantenemos los demás mensajes tal cual
            }
        }

        // 3. Guardamos la lista completa (ahora con todos los mensajes)
        local.saveMessages(updatedList)
    }
}
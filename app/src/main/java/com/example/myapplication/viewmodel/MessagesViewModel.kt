package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.AddMessageUseCase
import com.example.myapplication.domain.usecase.DeleteMessageUseCase
import com.example.myapplication.domain.usecase.GetMessagesUseCase
import com.example.myapplication.domain.usecase.UpdateMessageUseCase
import com.example.myapplication.model.data.entities.ReminderMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesViewModel(
    private val getMessages: GetMessagesUseCase,
    private val addMessage: AddMessageUseCase,
    private val deleteMessage: DeleteMessageUseCase,
    private val updateMessage: UpdateMessageUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ReminderMessage>>(emptyList())
    val messages: StateFlow<List<ReminderMessage>> = _messages

    init { loadMessages() }

    fun loadMessages() = viewModelScope.launch {
        _messages.value = getMessages()
    }

    fun add(text: String) = viewModelScope.launch {
        addMessage(text)
        loadMessages()
    }

    fun delete(id: Long) = viewModelScope.launch {
        deleteMessage(id)
        loadMessages()
    }
    fun toggleFavorite(id: Long) = viewModelScope.launch {
        // 1. Buscamos el mensaje en la lista actual
        val messageToUpdate = _messages.value.find { it.id == id }

        messageToUpdate?.let { msg ->
            // 2. Creamos la copia con el estado invertido
            val updatedMsg = msg.copy(isFavorite = !msg.isFavorite)

            // 3. PERSISTENCIA: Guardamos el cambio en DataStore a través del UseCase
            updateMessage(updatedMsg.id)

            // 4. Refrescamos la UI desde la fuente de verdad (DataStore)
            loadMessages()
        }
    }
}
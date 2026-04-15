package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.AddMessageUseCase
import com.example.myapplication.domain.usecase.DeleteMessageUseCase
import com.example.myapplication.domain.usecase.GetMessagesUseCase
import com.example.myapplication.model.data.entities.ReminderMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesViewModel(
    private val getMessages: GetMessagesUseCase,
    private val addMessage: AddMessageUseCase,
    private val deleteMessage: DeleteMessageUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ReminderMessage>>(emptyList())
    val messages: StateFlow<List<ReminderMessage>> = _messages

    init { loadMessages() }

    private fun loadMessages() = viewModelScope.launch {
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
}
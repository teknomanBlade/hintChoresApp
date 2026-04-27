package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetMessagesUseCase
import com.example.myapplication.domain.usecase.SaveSelectedMessageUseCase
import com.example.myapplication.model.data.entities.ReminderMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.invoke

class MessagesPickerViewModel(private val getMessages: GetMessagesUseCase,
    private val saveSelectedMessageUseCase: SaveSelectedMessageUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ReminderMessage>>(emptyList())
    val messages: StateFlow<List<ReminderMessage>> = _messages

    init {
        getMessages()
    }
    fun getMessages() = viewModelScope.launch { _messages.value = getMessages.invoke() }

    fun saveMessageSelected(message: String) = viewModelScope.launch { saveSelectedMessageUseCase.invoke(message) }
}
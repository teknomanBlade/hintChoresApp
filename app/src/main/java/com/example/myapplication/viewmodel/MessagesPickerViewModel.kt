package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetMessagesUseCase
import com.example.myapplication.model.data.entities.ReminderMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesPickerViewModel(private val getMessages: GetMessagesUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ReminderMessage>>(emptyList())
    val messages: StateFlow<List<ReminderMessage>> = _messages

    init {
        viewModelScope.launch {
            _messages.value = getMessages.invoke()
        }
    }
}
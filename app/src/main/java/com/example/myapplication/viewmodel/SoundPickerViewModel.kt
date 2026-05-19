package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.messages.IMessagesRepository
import com.example.myapplication.domain.model.NotificationSound
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SoundPickerViewModel(
    private val repository: IMessagesRepository
) : ViewModel() {

    // Obtenemos el sonido guardado o uno por defecto
    val selectedSound: StateFlow<NotificationSound> = repository.getSelectedSound()
        .map { id ->
            NotificationSound::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .find { it.channelId == id } ?: NotificationSound.Default // Asumiendo que tienes un Default
        }
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000), NotificationSound.Default)

    fun onSoundSelected(sound: NotificationSound) {
        viewModelScope.launch {
            repository.saveSelectedSound(sound.channelId)
        }
    }
}
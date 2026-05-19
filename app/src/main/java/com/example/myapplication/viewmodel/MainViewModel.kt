package com.example.myapplication.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.data.coroutines.ReminderUIState
import com.example.myapplication.domain.model.NotificationSound
import com.example.myapplication.domain.model.ReminderDelay
import com.example.myapplication.domain.model.ReminderSound
import com.example.myapplication.domain.model.TimeUnitType
import com.example.myapplication.domain.usecase.AddMessageUseCase
import com.example.myapplication.domain.usecase.CreateReminderUseCase
import com.example.myapplication.domain.usecase.CreateReminderWithSFXUseCase
import com.example.myapplication.domain.usecase.CreateReminderWithTimePickerUseCase
import com.example.myapplication.domain.usecase.GetFavoriteMessageUseCase
import com.example.myapplication.domain.usecase.GetSelectedMessageUseCase
import com.example.myapplication.domain.usecase.GetSelectedSoundUseCase
import com.example.myapplication.model.data.repository.ReminderRepository
import com.example.myapplication.model.service.ShakeService
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val repository: ReminderRepository,
    private val createReminder: CreateReminderUseCase,
    private val createReminderWithTimePickerUseCase: CreateReminderWithTimePickerUseCase,
    private val createReminderWithSFXUseCase: CreateReminderWithSFXUseCase,
    private val addReminderMessage: AddMessageUseCase,
    private val getFavoriteMessageUseCase: GetFavoriteMessageUseCase,
    private val getSelectedMessageUseCase: GetSelectedMessageUseCase,
    private val getSelectedSoundUseCase: GetSelectedSoundUseCase
) : AndroidViewModel(application) {

    var uiState by mutableStateOf(ReminderUIState())
        private set

    fun onAmountChange(value: String) {
        uiState = uiState.copy(amount = value.filter { it.isDigit() })
    }

    fun onUnitSelected(unit: TimeUnitType) {
        uiState = uiState.copy(selectedUnit = unit)
    }

    var delayMinutes by mutableIntStateOf(repository.getDelay())
        private set

    var serviceEnabled by mutableStateOf(false)
        private set

    fun addMessage(message:String){
        viewModelScope.launch {
            addReminderMessage.invoke(message)
        }
    }
    fun onDelaySelected(minutes: Int) {
        delayMinutes = minutes
        repository.saveDelay(minutes)
    }
    fun createPhotoReminder(message:String, imagePath: String) {
        viewModelScope.launch {
            createReminder.invoke(message, imagePath)
        }
    }
    fun createPhotoReminderWithTimeAndSoundPicker(imagePath: String){
        val amount = uiState.amount.toIntOrNull() ?: return
        viewModelScope.launch {
            // 1. Obtenemos los valores de los UseCases (asumiendo que devuelven Flow o son suspend)
            val selectedMessageText = getSelectedMessageUseCase.invoke()?.text ?: "Recordatorio"

            // 2. Obtenemos el ID del sonido y lo convertimos al objeto sealed class
            val soundObject = mapIdToNotificationSound(getSelectedSoundUseCase.invoke())

            // 3. Invocamos el UseCase con el objeto correcto
            createReminderWithSFXUseCase.invoke(
                message = selectedMessageText,
                imagePath = imagePath,
                reminderDelay = ReminderDelay(amount, uiState.selectedUnit),
                sound = soundObject
            )
        }
    }
    fun createPhotoReminderWithTimePicker(imagePath: String) {
        val amount = uiState.amount.toIntOrNull() ?: return
        viewModelScope.launch {
            createReminderWithTimePickerUseCase.invoke(getSelectedMessageUseCase.invoke()?.text.toString(), imagePath, ReminderDelay(amount, uiState.selectedUnit))
        }
    }
    fun createReminderWithFavoriteMessage(imagePath: String){
        viewModelScope.launch {
            createReminder.invoke(getFavoriteMessageUseCase.invoke()?.text.toString())
        }
    }
    fun toggleService(context: Context, enable: Boolean) {
        serviceEnabled = enable

        if (enable) {
            context.startForegroundService(Intent(context, ShakeService::class.java))
        } else {
            context.stopService(Intent(context, ShakeService::class.java))
        }
    }
    private fun mapIdToNotificationSound(id: String?): NotificationSound {
        return NotificationSound::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .find { it.channelId == id } ?: NotificationSound.Default // Default si no lo encuentra
    }
}
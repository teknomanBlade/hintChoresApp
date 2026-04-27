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
import com.example.myapplication.domain.model.ReminderDelay
import com.example.myapplication.domain.model.TimeUnitType
import com.example.myapplication.domain.usecase.AddMessageUseCase
import com.example.myapplication.domain.usecase.CreateReminderUseCase
import com.example.myapplication.domain.usecase.CreateReminderWithTimePickerUseCase
import com.example.myapplication.domain.usecase.GetFavoriteMessageUseCase
import com.example.myapplication.domain.usecase.GetSelectedMessageUseCase
import com.example.myapplication.model.data.repository.ReminderRepository
import com.example.myapplication.model.service.ShakeService
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val repository: ReminderRepository,
    private val createReminder: CreateReminderUseCase,
    private val createReminderWithTimePickerUseCase: CreateReminderWithTimePickerUseCase,
    private val addReminderMessage: AddMessageUseCase,
    private val getFavoriteMessageUseCase: GetFavoriteMessageUseCase,
    private val getSelectedMessageUseCase: GetSelectedMessageUseCase
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
}
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
import com.example.myapplication.domain.usecase.AddMessageUseCase
import com.example.myapplication.domain.usecase.CreateReminderUseCase
import com.example.myapplication.model.data.repository.ReminderRepository
import com.example.myapplication.model.service.ShakeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val repository: ReminderRepository,
    private val createReminder: CreateReminderUseCase,
    private val createReminderMessage: AddMessageUseCase
) : AndroidViewModel(application) {

    var delayMinutes by mutableIntStateOf(repository.getDelay())
        private set

    var serviceEnabled by mutableStateOf(false)
        private set

    fun addMessage(message:String){
        viewModelScope.launch {
            createReminderMessage.invoke(message)
        }
    }
    fun onDelaySelected(minutes: Int) {
        delayMinutes = minutes
        repository.saveDelay(minutes)
    }
    fun createPhotoReminder(message:String, imagePath: String) {
        createReminder.invoke(message, imagePath)
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
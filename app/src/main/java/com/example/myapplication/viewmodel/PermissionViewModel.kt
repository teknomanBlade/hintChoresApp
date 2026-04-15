package com.example.myapplication.viewmodel

import android.Manifest
import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.firstrun.FirstRunManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PermissionViewModel(application: Application, private val firstRunManager: FirstRunManager): AndroidViewModel(application) {

    val permissions = buildList {
        if (Build.VERSION.SDK_INT >= 33)
            add(Manifest.permission.POST_NOTIFICATIONS)

        add(Manifest.permission.CAMERA)
    }.toTypedArray()

    private val _isFirstRun = MutableStateFlow<Boolean?>(null) // null significa "cargando"

    // Estado público (Solo lectura) que observará el NavHost
    val isFirstRun: StateFlow<Boolean?> = _isFirstRun.asStateFlow()

    init {
        checkFirstRunStatus()
    }

    private fun checkFirstRunStatus() {
        viewModelScope.launch {
            _isFirstRun.value = firstRunManager.isFirstRun()
        }
    }

    fun setFirstRunCompleted(){
        viewModelScope.launch {
            firstRunManager.setFirstRunCompleted()
            _isFirstRun.value = false
        }
    }
}
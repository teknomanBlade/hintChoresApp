package com.example.myapplication.model.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.domain.coroutines.IDispatchersProvider
import com.example.myapplication.domain.usecase.CreateReminderUseCase
import com.example.myapplication.domain.usecase.GetFavoriteMessageUseCase
import com.example.myapplication.model.data.ShakeDetector
import com.example.myapplication.model.data.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ShakeService: Service() {

    private val dispatchersProvider : IDispatchersProvider by inject()
    private val serviceScope = CoroutineScope(dispatchersProvider.io)
    private val reminderUseCase: CreateReminderUseCase by inject()
    private val getFavoriteMessageUseCase: GetFavoriteMessageUseCase by inject()
    private lateinit var sensorManager: SensorManager
    private var lastShakeTime: Long = 0
    private val SHAKE_THRESHOLD_MS = 2000
    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val detector = ShakeDetector {
            val currentTime = System.currentTimeMillis()

            // Solo actuar si pasaron más de 2 segundos desde el último éxito
            if (currentTime - lastShakeTime > SHAKE_THRESHOLD_MS) {
                lastShakeTime = currentTime // Actualizar el tiempo del último movimiento

                serviceScope.launch {
                    val favoriteMessage = getFavoriteMessageUseCase.invoke()
                    // Usar el mensaje favorito si existe, sino un texto por defecto
                    val text = favoriteMessage?.text ?: "Recordatorio automático"
                    reminderUseCase.invoke(text)
                }
            }
        }

        sensorManager.registerListener(
            detector,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        val channelId = "shake_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Shake Detection Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Shake Detection Active")
            .setContentText("Listening for shakes to schedule reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        } else {
            startForeground(1, notification)
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    override fun onBind(intent: Intent?) = null
}
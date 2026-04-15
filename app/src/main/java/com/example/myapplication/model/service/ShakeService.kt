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
import com.example.myapplication.model.data.ShakeDetector
import com.example.myapplication.model.data.repository.ReminderRepository
import org.koin.android.ext.android.inject

class ShakeService: Service() {
    private val repository: ReminderRepository by inject()
    private lateinit var sensorManager: SensorManager

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val detector = ShakeDetector {
            repository.scheduleReminder("Recordatorio desde ShakeService")
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
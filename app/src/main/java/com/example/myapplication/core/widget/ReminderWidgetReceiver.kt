package com.example.myapplication.core.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myapplication.model.data.ReminderScheduler
import com.example.myapplication.view.activities.WidgetCameraActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReminderWidgetReceiver : BroadcastReceiver(), KoinComponent {

    private val reminderScheduler: ReminderScheduler by inject()

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {

            "ACTION_QUICK_REMINDER" -> {
                // 🔔 dispara notificación directa
                reminderScheduler.scheduleReminder("Mensaje de prueba de recordatorio")
            }

            "ACTION_CAMERA_REMINDER" -> {
                // 📸 abre Activity de cámara
                val intent = Intent(context, WidgetCameraActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        }
    }
}
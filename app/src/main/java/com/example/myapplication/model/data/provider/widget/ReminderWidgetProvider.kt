package com.example.myapplication.model.data.provider.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.core.widget.ReminderWidgetReceiver

class ReminderWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Toast.makeText(context, "onUpdate!", Toast.LENGTH_LONG).show()
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(
                context.packageName,
                R.layout.widget_reminder
            )

            // Intent botón rápido
            val quickIntent = Intent(context, ReminderWidgetReceiver::class.java)
            quickIntent.action = "ACTION_QUICK_REMINDER"

            val quickPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                quickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            views.setOnClickPendingIntent(R.id.btnQuick, quickPendingIntent)

            // Intent botón cámara
            val cameraIntent = Intent(context, ReminderWidgetReceiver::class.java)
            cameraIntent.action = "ACTION_CAMERA_REMINDER"

            val cameraPendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                cameraIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            views.setOnClickPendingIntent(R.id.btnCamera, cameraPendingIntent)

            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
package com.example.myapplication.model.data

import android.content.Context
import androidx.core.content.edit

class ReminderPreferences(private val context: Context) {

    private val prefs =
        context.getSharedPreferences("reminder_prefs", Context.MODE_PRIVATE)

    fun saveDelay(minutes: Int) {
        prefs.edit { putInt("delay_minutes", minutes) }
    }

    fun getDelay(): Int {
        return prefs.getInt("delay_minutes", 60)
    }
}
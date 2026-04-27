package com.example.myapplication.model.data.repository

import com.example.myapplication.domain.model.ReminderDelay
import com.example.myapplication.model.data.ReminderPreferences
import com.example.myapplication.model.data.ReminderScheduler

class ReminderRepository(private val prefs : ReminderPreferences,
                         private val scheduler : ReminderScheduler) {

    fun saveDelay(minutes: Int) = prefs.saveDelay(minutes)

    fun getDelay(): Int = prefs.getDelay()

    fun scheduleReminder(message:String,imagePath: String? = null) {
        scheduler.scheduleReminder(message,imagePath)
    }
    fun scheduleReminder(message:String,imagePath: String? = null, reminderDelay: ReminderDelay){
        scheduler.scheduleReminder(message,imagePath,reminderDelay)
    }
}
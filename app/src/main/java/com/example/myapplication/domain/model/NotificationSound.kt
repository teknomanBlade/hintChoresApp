package com.example.myapplication.domain.model

sealed class NotificationSound(val channelId: String) {
    object Default : NotificationSound("reminder_default")
    object Soft : NotificationSound("reminder_soft")
    object Alarm : NotificationSound("reminder_alarm")
    object Silent : NotificationSound("reminder_silent")
}
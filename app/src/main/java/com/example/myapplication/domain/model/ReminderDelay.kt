package com.example.myapplication.domain.model

enum class TimeUnitType {
    MINUTES,
    HOURS,
    DAYS,
    MONTHS
}

data class ReminderDelay(
    val amount: Int,
    val unit: TimeUnitType
)

package com.example.myapplication.domain.extensions

import com.example.myapplication.domain.model.ReminderDelay
import com.example.myapplication.domain.model.TimeUnitType

fun ReminderDelay.toMillis(): Long {
    val minutes = when (unit) {
        TimeUnitType.MINUTES -> amount.toLong()
        TimeUnitType.HOURS -> amount * 60L
        TimeUnitType.DAYS -> amount * 60L * 24
        TimeUnitType.MONTHS -> amount * 60L * 24 * 30 // simplificación OK
    }

    return minutes * 60_000
}
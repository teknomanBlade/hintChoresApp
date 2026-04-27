package com.example.myapplication.core.data.coroutines

import com.example.myapplication.domain.model.TimeUnitType

data class ReminderUIState(
    val amount: String = "1",
    val selectedUnit: TimeUnitType = TimeUnitType.HOURS
)
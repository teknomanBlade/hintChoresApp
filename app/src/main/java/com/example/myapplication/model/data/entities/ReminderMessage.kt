package com.example.myapplication.model.data.entities

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ReminderMessage(
    val id: Long,
    val text: String,
    val isFavorite: Boolean = false
)
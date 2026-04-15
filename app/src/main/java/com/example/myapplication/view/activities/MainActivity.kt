package com.example.myapplication.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.model.data.provider.NotificationProvider
import com.example.myapplication.view.HintChoresApp

class MainActivity : ComponentActivity() {
    private val notificationProvider = NotificationProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationProvider.createChannel(this)
        setContent {
            HintChoresApp()
        }
    }
}
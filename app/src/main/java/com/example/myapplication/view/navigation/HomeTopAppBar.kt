package com.example.myapplication.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(onAppInfo: () -> Unit = {}, onMessageSelection: () -> Unit = {}){
    TopAppBar(
        title = {
            Text(
                "Recordatorios de tareas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
            )
           }, colors = TopAppBarDefaults.topAppBarColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer,
              titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer)
           , actions = {
            IconButton(onClick = { onAppInfo() }) {
                Icon(painter = painterResource(id = R.drawable.ic_app_info),
                    contentDescription = "App info",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            IconButton(onClick = { onMessageSelection() }) {
                Icon(painter = painterResource(id = R.drawable.ic_message_select),
                    contentDescription = "Message selection",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        })
}
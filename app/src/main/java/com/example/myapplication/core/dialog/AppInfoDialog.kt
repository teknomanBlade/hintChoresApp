package com.example.myapplication.core.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppInfoDialog(onDismiss: () -> Unit){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Información de la aplicación")
        },
        text = {
            Text("Aplicación generada por Marcelo Luna")
        },
        confirmButton = {}
    )
}
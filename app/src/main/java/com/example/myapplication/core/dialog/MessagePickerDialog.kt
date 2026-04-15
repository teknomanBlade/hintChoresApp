package com.example.myapplication.core.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.data.entities.ReminderMessage
import com.example.myapplication.view.messages.MessageRow
import com.example.myapplication.viewmodel.MessagesPickerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MessagePickerDialog(
    messages: List<ReminderMessage>,
    onSelected: (ReminderMessage) -> Unit,
    onDismiss: () -> Unit,
    vm: MessagesPickerViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        vm.getMessages()
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Elegir mensaje de recordatorio")
        },
        text = {

            if (messages.isEmpty()) {
                Text("No tenés mensajes creados todavía")
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp) // 👈 clave para scroll
                ) {
                    items(messages) { msg ->
                        MessageRow(msg, onSelected)
                    }
                }
            }
        },
        confirmButton = {}
    )
}
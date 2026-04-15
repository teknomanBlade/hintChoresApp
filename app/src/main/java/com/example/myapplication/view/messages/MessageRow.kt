package com.example.myapplication.view.messages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.data.entities.ReminderMessage

@Composable
fun MessageRow(
    msg: ReminderMessage,
    onSelected: (ReminderMessage) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = { onSelected(msg) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = msg.text,
                fontSize = 16.sp
            )
        }
    }
}
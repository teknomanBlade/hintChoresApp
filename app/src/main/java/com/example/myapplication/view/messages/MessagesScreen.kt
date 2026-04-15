package com.example.myapplication.view.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.viewmodel.MessagesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MessagesScreen(vm: MessagesViewModel = koinViewModel()) {

    val messages by vm.messages.collectAsState()
    val sortedMessages = messages.sortedByDescending { it.isFavorite }
    var text by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {

        Text("Mensajes de recordatorio", fontSize = 22.sp)

        Spacer(Modifier.height(12.dp))

        Row {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ej: Tomar medicación") }
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                if (text.isNotBlank()) {
                    vm.add(text)
                    text = ""
                }
            }) {
                Text("Agregar")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(sortedMessages) { msg ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(msg.text, Modifier.weight(1f))
                        IconButton(onClick = { vm.delete(msg.id) }) {
                            Text("🗑")
                        }
                        IconButton(onClick = { vm.toggleFavorite(msg.id) }) {
                            Icon(
                                imageVector = if (msg.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (msg.isFavorite) Color(0xFFFFD700) else Color.Gray // Dorado si es favorito
                            )
                        }

                    }
                }
            }
        }
    }
}
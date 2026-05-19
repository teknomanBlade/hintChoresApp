package com.example.myapplication.view.soundpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.NotificationSound

@Composable
fun SoundPicker(
    selected: NotificationSound,
    onSelected: (NotificationSound) -> Unit
) {
    Column {
        NotificationSound::class.sealedSubclasses.forEach { clazz ->
            val sound = clazz.objectInstance ?: return@forEach

            // Añadimos clickable a toda la fila para mejor UX
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelected(sound) }
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selected == sound,
                    onClick = { onSelected(sound) }
                )
                Text(
                    text = sound.channelId,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
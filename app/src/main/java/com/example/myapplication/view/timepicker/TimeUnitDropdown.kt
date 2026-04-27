package com.example.myapplication.view.timepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.domain.model.TimeUnitType

@Composable
fun TimeUnitDropdown(
    selected: TimeUnitType,
    onSelected: (TimeUnitType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box() {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected.name)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TimeUnitType.entries.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) },
                    onClick = {
                        expanded = false
                        onSelected(unit)
                    }
                )
            }
        }
    }
}
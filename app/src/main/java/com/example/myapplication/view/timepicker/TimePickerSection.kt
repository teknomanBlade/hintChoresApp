package com.example.myapplication.view.timepicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.TimeUnitType

@Composable
fun TimePickerSection(
    amount: String,
    selectedUnit: TimeUnitType,
    onAmountChange: (String) -> Unit,
    onUnitSelected: (TimeUnitType) -> Unit
) {

    Column() {
        Text("Recordarme en:")

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                modifier = Modifier.width(90.dp),
                singleLine = true,
                label = { Text("Cantidad") }
            )

            Spacer(Modifier.width(12.dp))

            TimeUnitDropdown(selectedUnit, onUnitSelected)
        }
    }
}
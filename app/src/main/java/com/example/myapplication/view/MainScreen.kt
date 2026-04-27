package com.example.myapplication.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.view.timepicker.TimePickerSection
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel) {

    val context = LocalContext.current
    var serviceEnabled by remember { mutableStateOf(false) }
    var selectedDelay by remember { mutableIntStateOf(60) }

    val delayOptions = listOf(1,15, 30, 60, 120)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Agitá el teléfono para crear un recordatorio automático")
        Spacer(Modifier.height(8.dp))

        // 🔘 Switch activar servicio
        Card(
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.width(300.dp)) {
                    Text("Activar gesto de agitar", fontWeight = FontWeight.Bold)
                    Text("El servicio quedará activo en segundo plano", textAlign = TextAlign.Justify)

                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Switch(
                        checked = vm.serviceEnabled,
                        onCheckedChange = {
                            vm.toggleService(context, it)
                        }
                    )
                }
            }
        }

        // ⏱️ Selector de tiempo
        Card(
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.fillMaxWidth()) {
                    Text("Tiempo del recordatorio", fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(8.dp))

                    TimePickerSection(
                        amount = vm.uiState.amount,
                        selectedUnit = vm.uiState.selectedUnit,
                        onAmountChange = vm::onAmountChange,
                        onUnitSelected = vm::onUnitSelected
                    )
                }
            }
        }
        // 📸 Botón recordatorio manual con foto (futuro)
        CameraReminderButton(vm)
    }
}
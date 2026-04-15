package com.example.myapplication.view

import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.myapplication.viewmodel.MainViewModel
import java.io.File

@Composable
fun CameraReminderButton(vm: MainViewModel) {

    val context = LocalContext.current
    var photoPath by remember { mutableStateOf("") }

    // Crear archivo temporal
    fun createImageFile(): File {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("reminder_", ".jpg", dir)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            vm.createPhotoReminder("Mensaje de prueba",photoPath)
            Toast.makeText(context, "Recordatorio creado 📸", Toast.LENGTH_SHORT).show()
        }
    }

    Button(
        onClick = {
            val file = createImageFile()
            photoPath = file.absolutePath

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            cameraLauncher.launch(uri)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Crear recordatorio con foto 📸")
    }
}
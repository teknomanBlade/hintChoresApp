package com.example.myapplication.view.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import com.example.myapplication.domain.usecase.CreateReminderUseCase
import com.example.myapplication.model.data.provider.NotificationProvider
import com.example.myapplication.view.activities.ui.theme.HintChoresAppTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class WidgetCameraActivity : ComponentActivity(), KoinComponent {
    private val createReminderUseCase: CreateReminderUseCase by inject()
    private lateinit var photoUri: Uri

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                createReminderUseCase.invoke(photoUri.toString())
            }
            finish() // cerrar activity siempre
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openCamera()
    }
    private fun openCamera() {
        val photoFile = createImageFile()
        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            photoFile
        )

        takePicture.launch(photoUri)
    }

    private fun createImageFile(): File {
        val dir = File(externalCacheDir, "images")
        dir.mkdirs()

        return File.createTempFile(
            "reminder_${System.currentTimeMillis()}",
            ".jpg",
            dir
        )
    }
}
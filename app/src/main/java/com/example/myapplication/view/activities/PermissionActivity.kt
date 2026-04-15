package com.example.myapplication.view.activities

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.core.firstrun.FirstRunManager
import com.example.myapplication.view.permissions.PermissionScreen
import com.example.myapplication.view.ui.theme.HintChoresAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PermissionActivity : ComponentActivity() {
    private val firstRunManager: FirstRunManager by inject()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private val permissions = buildList {
        if (Build.VERSION.SDK_INT >= 33)
            add(Manifest.permission.POST_NOTIFICATIONS)

        add(Manifest.permission.CAMERA)
    }.toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        scope.launch {
            if (!firstRunManager.isFirstRun()) {
                goToMain()
                return@launch
            }
        }
        setContent {
            HintChoresAppTheme {
                PermissionScreen(
                    onPermissionsGranted = {
                        scope.launch {
                            firstRunManager.setFirstRunCompleted()
                            goToMain()
                        }
                    },
                    permissions = permissions
                )
            }
        }
    }
    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
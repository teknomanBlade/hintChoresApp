package com.example.myapplication.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.view.navigation.MainAppScreen
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.PermissionViewModel
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun HintChoresApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val vmPermissions : PermissionViewModel = koinViewModel()
            MainAppScreen(vmPermissions)
        }
    }
}
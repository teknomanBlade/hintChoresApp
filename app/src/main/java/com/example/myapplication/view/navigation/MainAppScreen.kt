package com.example.myapplication.view.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.dialog.AppInfoDialog
import com.example.myapplication.viewmodel.PermissionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainAppScreen(viewModel: PermissionViewModel = koinViewModel()) {
    val navController = rememberNavController()
    var showInfoDialog by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isFirstRun by viewModel.isFirstRun.collectAsState()

    // Mientras el valor sea null (está cargando de DataStore), podemos mostrar un Splash o nada
    if (isFirstRun == null) {
        // Pantalla de carga opcional
        return
    }

    val startRoute = if (isFirstRun == true) {
        Screen.PermissionsScreen.route
    } else {
        Screen.MainScreen.route
    }

    Scaffold(
        topBar = { HomeTopAppBar { showInfoDialog = true } },
        bottomBar = {
            // Solo mostramos la barra si NO estamos en la pantalla de permisos
            if (currentRoute != Screen.PermissionsScreen.route) {
                BottomNavBar(navController, currentRoute)
            }
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            // Si ya aceptó permisos antes, va directo a Main, si no a Permissions
            startDestination = startRoute
        )
    }
    if(showInfoDialog){
        AppInfoDialog { showInfoDialog = false }
    }
}
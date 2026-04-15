package com.example.myapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.view.MainScreen
import com.example.myapplication.view.messages.MessagesScreen
import com.example.myapplication.view.permissions.PermissionScreen
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.PermissionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String // Aquí pasarás Screen.PermissionsScreen.route o Screen.MainScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // --- PANTALLA DE PERMISOS ---
        composable(Screen.PermissionsScreen.route) {
            // Reemplaza con tu Composable de Permisos real
            val permissionsViewModel: PermissionViewModel = koinViewModel()
            PermissionScreen(
                onPermissionsGranted = {
                    permissionsViewModel.setFirstRunCompleted()
                    // Al conceder permisos, navegamos a la MainScreen
                    navController.navigate(Screen.MainScreen.route) {
                        // IMPORTANTE: Eliminamos la pantalla de permisos del historial
                        // para que el usuario no pueda volver atrás a ella.
                        popUpTo(Screen.PermissionsScreen.route) { inclusive = true }
                    }
                },
                permissions = permissionsViewModel.permissions
            )
        }

        // --- PANTALLA PRINCIPAL (Home) ---
        composable(Screen.MainScreen.route) {
            // Reemplaza con tu Composable de la pantalla principal
            val vm : MainViewModel = koinViewModel()
            MainScreen(vm)
        }

        // --- PANTALLA DE MENSAJES ---
        composable(Screen.MessagesScreen.route) {
            // Reemplaza con tu Composable de mensajes
            MessagesScreen()
        }
    }
}
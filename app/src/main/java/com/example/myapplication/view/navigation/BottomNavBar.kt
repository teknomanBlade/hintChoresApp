package com.example.myapplication.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar {
        // Usamos la lista que ya tienes en Screen.kt
        itemsBottomNav.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    // Evitamos navegar si ya estamos en esa pantalla
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            // Volver a la pantalla de inicio al presionar atrás
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    // Aquí eliges el icono según la pantalla
                    val icon = if (screen == Screen.MainScreen) Icons.Default.Home else Icons.Default.Email
                    Icon(imageVector = icon, contentDescription = null)
                },
                label = {
                    Text(if (screen == Screen.MainScreen) "Inicio" else "Mensajes")
                }
            )
        }
    }
}
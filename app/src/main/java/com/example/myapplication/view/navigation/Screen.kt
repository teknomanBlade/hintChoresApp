package com.example.myapplication.view.navigation

sealed class Screen(val route:String){
    object MainScreen: Screen("main_screen")
    object MessagesScreen: Screen("messages_screen")
    object PermissionsScreen: Screen("permissions_screen")
}

val itemsBottomNav = listOf(Screen.MainScreen, Screen.MessagesScreen)
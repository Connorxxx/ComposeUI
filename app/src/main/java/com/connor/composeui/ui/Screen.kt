package com.connor.composeui.ui

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Contact : Screen("contact")
    data object Settings : Screen("settings")
    data object AddContact : Screen("addContact")
}
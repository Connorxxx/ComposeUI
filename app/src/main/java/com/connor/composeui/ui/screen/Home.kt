package com.connor.composeui.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.connor.composeui.models.event.HomeEvent
import com.connor.composeui.ui.Screen
import com.connor.composeui.utils.navigateSingleTopTo

@Composable
fun Home(onHomeEvent: (HomeEvent) -> Unit) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { BottomBar(navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Contact.route,
            Modifier.padding(it)
        ) {
            composable(Screen.Contact.route) {
                Contact(onHomeEvent = onHomeEvent)
            }
            composable(Screen.Settings.route) {
                Settings()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Home", fontWeight = FontWeight.W400) },
    )
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val navigateToScreen: (String) -> Unit = { navController.navigateSingleTopTo(it) }
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        listOf(Screen.Contact, Screen.Settings).forEach { screen ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(screen.route) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navigateToScreen(screen.route)
                }
            )
        }
    }
}
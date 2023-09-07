package com.connor.composeui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.connor.composeui.models.event.HomeEvent
import com.connor.composeui.ui.Screen
import com.connor.composeui.ui.screen.AddContact
import com.connor.composeui.ui.screen.Home
import com.connor.composeui.ui.theme.ComposeUITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHome()
                }
            }
        }
    }
}

@Composable
fun NavHome() {
    val navController = rememberNavController()
    val homeEvent: (HomeEvent) -> Unit = {
        when(it) {
            is HomeEvent.NavigateTo ->  navController.navigate(it.route)
            is HomeEvent.Back -> navController.popBackStack()
        }
    }
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            Home(homeEvent)
        }
        composable(Screen.AddContact.route) {
            AddContact(onHomeEvent = homeEvent)
        }
    }
}


package com.example.turdusportfolio.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Router(title: String) {
   HomeScreen("Home")
}

@Composable
fun RouterApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Router.HomeScreen.name,
    ) {
        composable(route = Router.HomeScreen.name) {
            HomeScreen()
        }
    }
}
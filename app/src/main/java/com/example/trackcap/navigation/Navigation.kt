package com.example.trackcap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackcap.ui.cards.view.homeScreen
import com.example.trackcap.ui.gastos.view.gastosScreen

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController,
        startDestination = NavigationState.Home.route,
        modifier = modifier) {

        composable(route = NavigationState.Home.route) {
            homeScreen()
        }

        composable(route = NavigationState.Gastos.route) {
            gastosScreen(navController = navController)
        }
    }
}
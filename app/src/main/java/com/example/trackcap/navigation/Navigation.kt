package com.example.trackcap.navigation

import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackcap.ui.cards.view.cardsScreen
import com.example.trackcap.ui.cards.view.homeScreen
import com.example.trackcap.ui.common.view.addCatScreen
import com.example.trackcap.ui.gastos.view.gastosScreen
import com.example.trackcap.ui.ingresos.view.ingresosScreen
import com.example.trackcap.ui.invest.view.investScreen
import com.example.trackcap.ui.login.view.loginScreen

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController,
        startDestination = NavigationState.Login.route,
        modifier = modifier) {

        composable(route = NavigationState.Home.route) {
            homeScreen(navController = navController)
        }

        composable(route = NavigationState.Invest.route) {
            investScreen(navController = navController)
        }

        composable(route = NavigationState.Gastos.route) {
            gastosScreen(navController = navController)
        }

        composable(route = NavigationState.Ingresos.route) {
            ingresosScreen(navController = navController)
        }

        composable(route = NavigationState.Cards.route) {
            cardsScreen(navController = navController)
        }

        composable(route = NavigationState.Add.route) {
            addCatScreen(navController = navController)
        }

        composable(route = NavigationState.Back.route) {
            navController.navigateUp()
        }

        composable(route = NavigationState.Login.route) {
            loginScreen(navController = navController)
        }

    }
}
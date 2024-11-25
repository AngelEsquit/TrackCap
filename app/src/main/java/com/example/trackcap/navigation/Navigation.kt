package com.example.trackcap.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackcap.ui.cards.view.AddCardScreen
import com.example.trackcap.ui.cards.view.CardsScreen
import com.example.trackcap.ui.cards.view.EditCardScreen
import com.example.trackcap.ui.cards.view.homeScreen
import com.example.trackcap.ui.cards.viewModel.CardsViewModel
import com.example.trackcap.ui.gastos.view.AddGastoScreen
import com.example.trackcap.ui.ingresos.view.addIngresoScreen
import com.example.trackcap.ui.gastos.view.gastosScreen
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import com.example.trackcap.ui.ingresos.view.ingresosScreen
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModel
import com.example.trackcap.ui.invest.view.InvestScreen
import com.example.trackcap.ui.invest.view.AddInvestScreen
import com.example.trackcap.ui.login.view.loginScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation(navController: NavHostController, gastosViewModel: GastosViewModel, ingresosViewModel: IngresosViewModel, viewModel: CardsViewModel, modifier: Modifier = Modifier) {
    NavHost(navController = navController,
        startDestination = NavigationState.Login.route,
        modifier = modifier) {

        composable(route = NavigationState.Home.route) {
            homeScreen(navController = navController)
        }

        composable(route = NavigationState.Invest.route) {
            InvestScreen(navController = navController)
        }

        composable(route = NavigationState.Gastos.route) {
            gastosScreen(navController = navController, gastosViewModel = gastosViewModel)
        }

        composable(route = NavigationState.Ingresos.route) {
            ingresosScreen(navController = navController, ingresosViewModel = ingresosViewModel)
        }

        composable(route = NavigationState.Cards.route) {
            CardsScreen(navController = navController, viewModel = viewModel)
        }

        composable("addCard") {
            AddCardScreen(navController = navController) { card ->
                viewModel.addCard(card)
            }
        }

        composable("editCard/{cardName}") { backStackEntry ->
            val cardName = backStackEntry.arguments?.getString("cardName")
            val card = viewModel.cards.value.find { it.name == cardName }
            if (card != null) {
                EditCardScreen(navController = navController, card = card, onUpdateCard = viewModel::updateCard, onDeleteCard = viewModel::deleteCard)
            }
        }

        composable(route = NavigationState.AddIngreso.route) {
            addIngresoScreen(navController = navController, IngresosViewModel = ingresosViewModel)
        }

        composable(route = NavigationState.AddGasto.route) {
            AddGastoScreen(navController = navController, GastosViewModel = gastosViewModel)
        }

        composable(route = NavigationState.Back.route) {
            navController.popBackStack()
        }

        composable(route = NavigationState.Login.route) {
            loginScreen(navController = navController)
        }

        composable(route = "addInvest") {
            AddInvestScreen(navController = navController)
        }
    }
}
package com.example.trackcap.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackcap.ui.cards.view.AddCardScreen
import com.example.trackcap.ui.cards.view.CardsScreen
import com.example.trackcap.ui.cards.view.DetailCardScreen
import com.example.trackcap.ui.cards.view.EditCardScreen
import com.example.trackcap.ui.cards.view.SelectCardScreen
import com.example.trackcap.ui.cards.view.homeScreen
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import com.example.trackcap.ui.gastos.view.AddGastoScreen
import com.example.trackcap.ui.gastos.view.GastosCard
import com.example.trackcap.ui.gastos.view.GastosListScreen
import com.example.trackcap.ui.ingresos.view.AddIngresoScreen
import com.example.trackcap.ui.gastos.view.GastosScreen
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import com.example.trackcap.ui.ingresos.view.IngresosListScreen
import com.example.trackcap.ui.ingresos.view.IngresosScreen
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModel
import com.example.trackcap.ui.invest.view.InvestScreen
import com.example.trackcap.ui.invest.view.AddInvestScreen
import com.example.trackcap.ui.login.view.LoginScreen
import com.example.trackcap.ui.resume.view.MoveListScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation(navController: NavHostController, gastosViewModel: GastosViewModel, ingresosViewModel: IngresosViewModel, cardsViewModel: Cards_ViewModel, modifier: Modifier = Modifier) {
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
            GastosScreen(navController = navController, gastosViewModel = gastosViewModel)
        }

        composable(route = NavigationState.Ingresos.route) {
            IngresosScreen(navController = navController, ingresosViewModel = ingresosViewModel)
        }

        composable(route = NavigationState.Cards.route) {
            CardsScreen(navController = navController, cardsViewModel = cardsViewModel)
        }

        composable("addCard") {
            AddCardScreen(navController = navController, Cards_ViewModel = cardsViewModel)
        }

        composable(route = NavigationState.SelectCard.route) {
            SelectCardScreen(navController = navController, cardsViewModel = cardsViewModel, gastosViewModel = gastosViewModel)
        }

        composable(route = NavigationState.EditCard.route) {
            EditCardScreen(navController = navController, cardsViewModel = cardsViewModel, card = cardsViewModel.selectedEditCard.value!!)
        }

        composable(route = NavigationState.DetailCard.route) {
            DetailCardScreen(navController = navController, cardsViewModel = cardsViewModel)
        }

        composable(route = NavigationState.GastosCard.route) {
            GastosCard(navController = navController, gastosViewModel = gastosViewModel, cardsViewmodel = cardsViewModel)
        }

        composable(route = NavigationState.AddIngreso.route) {
            AddIngresoScreen(navController = navController, IngresosViewModel = ingresosViewModel)
        }

        composable(route = NavigationState.IngresosCategory.route) {
            IngresosListScreen(navController = navController, IngresosViewModel = ingresosViewModel)
        }

        composable(route = NavigationState.GastosCategory.route) {
            GastosListScreen(navController = navController, GastosViewModel = gastosViewModel)
        }

        composable(route = NavigationState.AddGasto.route) {
            AddGastoScreen(navController = navController, GastosViewModel = gastosViewModel)
        }

        composable(route = NavigationState.Back.route) {
            navController.popBackStack()
        }

        composable(route = NavigationState.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = "addInvest") {
            AddInvestScreen(navController = navController)
        }

        composable(route = NavigationState.Resume.route) {
            MoveListScreen(navController = navController, GastosViewModel = gastosViewModel, IngresosViewModel = ingresosViewModel)
        }
    }
}
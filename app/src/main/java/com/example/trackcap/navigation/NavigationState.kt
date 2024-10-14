package com.example.trackcap.navigation

sealed class NavigationState(val route: String) {
    data object Home: NavigationState("home")
    data object Invest: NavigationState("invest")
    data object Cards: NavigationState("cards")
    data object Gastos: NavigationState("gastos")
    data object Ingresos: NavigationState("ingresos")
    data object Add: NavigationState("add")
    data object Back: NavigationState("back")
}
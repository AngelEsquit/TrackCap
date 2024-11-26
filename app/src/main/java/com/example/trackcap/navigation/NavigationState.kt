package com.example.trackcap.navigation

sealed class NavigationState(val route: String) {
    data object Home: NavigationState("home")
    data object Invest: NavigationState("invest")
    data object Cards: NavigationState("cards")
    data object Gastos: NavigationState("gastos")
    data object Ingresos: NavigationState("ingresos")
    data object AddIngreso: NavigationState("addIngreso")
    data object AddGasto: NavigationState("addGasto")
    data object Back: NavigationState("back")
    data object Login: NavigationState("login")
    data object Register: NavigationState("register")
    data object SelectCard: NavigationState("selectCard")
    data object EditCard: NavigationState("editCard")
    data object DetailCard: NavigationState("detailCard")
    data object GastosCard: NavigationState("gastosCard")
    data object IngresosCategory: NavigationState("ingresosCategory")
    data object GastosCategory: NavigationState("gastosCategory")
    data object Resume: NavigationState("resume")
}
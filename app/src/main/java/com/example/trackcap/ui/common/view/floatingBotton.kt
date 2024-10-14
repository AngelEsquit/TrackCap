package com.example.trackcap.ui.common.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo

@Composable
fun floatingBotton(navController: NavController, route: NavigationState) {
    FloatingActionButton(
        onClick = { navigateTo(navController, route.route) },
        modifier = Modifier
            .padding(16.dp) // Padding alrededor del botón
        // .offset(x = 16.dp, y = (-16).dp) // Ajustar la posición si es necesario
        // .size(64.dp) // Ajustar el tamaño si es necesario
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Agregar",
            // tint = Color.White // Personalizar el color del icono si es necesario
        )
    }
}

@Preview(showBackground = true)
@Composable
fun floatingBottonPreview() {
    val navController = rememberNavController()
    val route = NavigationState.Home
    floatingBotton(navController, route)
}
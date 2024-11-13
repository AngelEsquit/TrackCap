package com.example.trackcap.ui.login.view

import android.annotation.SuppressLint
import  androidx.compose.foundation.layout.*
import  androidx.compose.material3.*
import  androidx.compose.runtime.Composable
import  androidx.compose.ui.Alignment
import  androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import  androidx.compose.ui.unit.dp
import  androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun loginScreen(navController: NavController) {
    Scaffold () {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Iniciar sesión", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Correo") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Contraseña") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navigateTo(navController, NavigationState.Home.route) }) {
                Text("Iniciar sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun loginScreenPreview() {
    loginScreen(navController = rememberNavController())
}
package com.example.trackcap.ui.ingresos.view

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.PackageManagerCompat.UnusedAppRestrictionsStatus
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ingresosScreen(navController: NavController) {
    Scaffold (topBar = { AppBar(title = "Ingresos", navController = navController) }
    ) { innerPadding ->
        Text(text = "Ingresos Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun ingresosScreenPreview() {
    val navController = rememberNavController()
    ingresosScreen(navController = navController)
}
package com.example.trackcap.ui.gastos.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun gastosScreen(navController: NavController) {
    val categorias = listOf("Comida", "Transporte", "Entretenimiento", "Otros")
    Column {
        Row { // Selección de temporalidad
            Text(text = "Día/Semana/Mes/Año")
        }

        Row { // Gráfico de gastos

        }

        Row { // Texto
            Text(text = "Gastos según categorías")
        }

        LazyColumn {
            items(categorias.chunked(3)) { row ->
                Row {
                    row.forEach { categoria ->
                        Box(
                            modifier = Modifier
                                .background(color = Color.White)
                                .padding(8.dp), // Agrega padding
                            contentAlignment = Alignment.Center // Centra el texto
                        ) {
                            Text(text = categoria)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun gastosScreenPreview() {
    val navController = rememberNavController()
    gastosScreen(navController = navController)
}
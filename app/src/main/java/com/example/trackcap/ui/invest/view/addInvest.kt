package com.example.trackcap.ui.invest.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBar
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo
import com.example.trackcap.ui.common.view.floatingBotton
import com.example.trackcap.ui.common.view.listSelector

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun addInvestScreen(navController: NavController) {
    Scaffold (topBar = { AppBar(title = "Agregar inversión", navController = navController) },
        floatingActionButton = { floatingBotton(navController, NavigationState.Back) }
    ) { innerPadding ->
        // Inversión
        val categories = listOf("Comida", "Transporte", "Entretenimiento", "Salud", "Educación", "Otros")
        val metodoPago = listOf("Efectivo", "Tarjeta de crédito", "Tarjeta de débito", "Transferencia bancaria")
        var monto by remember { mutableStateOf("") }

        LazyColumn (
            contentPadding = innerPadding,
            modifier = Modifier.padding(16.dp)
        ){
            item { // Selección de categoría
                Box (modifier = Modifier.padding(vertical = 36.dp)) {
                    listSelector(label = "Categoría", options = categories, onSelected = { })
                }
            }

            item { // Monto
                Box {
                    TextField(
                        value = monto,
                        onValueChange = { monto = it },
                        label = { Text("Ingrese el monto") },
                        placeholder = { Text("Escribe aquí...") },
                        isError = monto.isEmpty(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }

            item { // Selección de método de pago
                Box (modifier = Modifier.padding(vertical = 36.dp)) {
                    listSelector(label = "Método de pago", options = metodoPago, onSelected = { })
                }
            }

            item {
                Button(onClick = { navigateTo(navController, NavigationState.Back.route) }) {
                    Text(text = "Agregar inversión")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun addInvestScreenPreview() {
    addInvestScreen(navController = rememberNavController())
}
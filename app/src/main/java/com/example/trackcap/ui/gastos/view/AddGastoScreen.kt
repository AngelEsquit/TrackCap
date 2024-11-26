package com.example.trackcap.ui.gastos.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo
import com.example.trackcap.ui.common.view.DatePickerField
import com.example.trackcap.ui.common.view.listSelector
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddGastoScreen(navController: NavController, GastosViewModel: GastosViewModel) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }

    Scaffold (topBar = { AppBarTop(title = "Agregar gasto", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (name.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty()) {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val localDate = LocalDate.parse(date, formatter)
                        val timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        if (paymentMethod == "Tarjeta de crédito") {
                            navigateTo(navController, NavigationState.SelectCard.route, popUpToRoute = NavigationState.AddGasto.route)
                        } else {
                            GastosViewModel.addGasto(name, amount.toDouble(), category, timestamp, paymentMethod)
                            navigateTo(navController, NavigationState.Gastos.route, popUpToRoute = NavigationState.AddGasto.route)
                        }
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar"
                )
            } }
    ) { innerPadding ->
        LazyColumn (
            contentPadding = innerPadding,
            modifier = Modifier.padding(16.dp)
        ){
            item { // Nombre del gasto
                TextField(value = name,
                    onValueChange = { name = it },
                    label = { Text("Ingrese el nombre del gasto") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = name.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }

            item { // Selección de categoría
                TextField(value = category,
                    onValueChange = { category = it },
                    label = { Text("Ingrese la categoría") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = category.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }

            item { // Monto
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Ingrese el monto") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = amount.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }
            
            item { // Método de pago
                val paymentMethods = listOf("Efectivo", "Tarjeta de crédito", "Tarjeta de débito", "Transferencia")
                listSelector(label = "Método de pago", options = paymentMethods, onSelected = { paymentMethod = it })
            }

            item { // Selección de fecha
                DatePickerField(
                    label = "Seleccionar fecha",
                    selectedDate = date,
                    onDateSelected = { date = it }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddGastoScreenPreview() {
    val navController = rememberNavController()
    val viewModel = viewModel(GastosViewModel::class.java)
    AddGastoScreen(navController = navController, viewModel)
}
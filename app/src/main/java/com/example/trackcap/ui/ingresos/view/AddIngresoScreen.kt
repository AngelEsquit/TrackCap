package com.example.trackcap.ui.ingresos.view

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
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddIngresoScreen(navController: NavController, IngresosViewModel: IngresosViewModel) {
    var name by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    Scaffold (topBar = { AppBarTop(title = "Agregar ingreso", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (name.isNotEmpty() && monto.isNotEmpty() && categoria.isNotEmpty() && fecha.isNotEmpty()) {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val localDate = LocalDate.parse(fecha, formatter)
                        val timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        IngresosViewModel.addIngreso(name, monto.toDouble(), categoria, timestamp)
                        navigateTo(navController, NavigationState.Ingresos.route, popUpToRoute = NavigationState.AddIngreso.route)
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
            item { // Nombre del ingreso
                TextField(value = name,
                    onValueChange = { name = it },
                    label = { Text("Ingrese el nombre del ingreso") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = name.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }

            item { // Selección de categoría
                TextField(value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Ingrese la categoría") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = categoria.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }

            item { // Monto
                TextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Ingrese el monto") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = monto.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }

            item { // Selección de fecha
                DatePickerField(
                    label = "Seleccionar fecha",
                    selectedDate = fecha,
                    onDateSelected = { fecha = it }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun addIngresoScreenPreview() {
    val navController = rememberNavController()
    val viewModel = viewModel(IngresosViewModel::class.java)
    AddIngresoScreen(navController = navController, viewModel)
}
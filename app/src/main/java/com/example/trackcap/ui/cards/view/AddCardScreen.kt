package com.example.trackcap.ui.cards.view

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.database.cards.CardItemEntity
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import com.example.trackcap.ui.common.view.DatePickerField
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddCardScreen(navController: NavController, Cards_ViewModel: Cards_ViewModel) {
    var name by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppBarTop(title = "Agregar Tarjeta", navController = navController)},
        bottomBar = { AppBarBottom(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (name.isNotEmpty() && paymentDate.isNotEmpty() && expiryDate.isNotEmpty() && balance.isNotEmpty()) {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val localDate = LocalDate.parse(paymentDate, formatter)
                        val timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        val formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val localDate2 = LocalDate.parse(expiryDate, formatter2)
                        val timestamp2 = localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        val card = CardItemEntity(name = name, payDate = timestamp, expiryDate = timestamp2, balance = balance.toDouble())

                        Cards_ViewModel.addCard(card)
                        navigateTo(navController, NavigationState.Cards.route, popUpToRoute = NavigationState.AddGasto.route)
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
            item { // Nombre de la tarjeta
                TextField(value = name,
                    onValueChange = { name = it },
                    label = { Text("Ingrese el nombre de la tarjeta") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = name.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
            }

            item { // Selección de fecha de pago
                DatePickerField(
                    label = "Seleccionar fecha de pago",
                    selectedDate = paymentDate,
                    onDateSelected = { paymentDate = it }
                )
            }

            item { // Selección de fecha de vencimiento
                DatePickerField(
                    label = "Seleccionar fecha de vencimiento",
                    selectedDate = expiryDate,
                    onDateSelected = { expiryDate = it }
                )
            }

            item { // Balance inicial
                TextField(
                    value = balance,
                    onValueChange = { balance = it },
                    label = { Text("Ingrese el balance inicial") },
                    placeholder = { Text("Escribe aquí...") },
                    isError = balance.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
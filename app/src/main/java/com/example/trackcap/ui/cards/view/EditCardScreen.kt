package com.example.trackcap.ui.cards.view

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.database.cards.CardItemEntity
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import com.example.trackcap.ui.common.view.DatePickerField
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EditCardScreen(navController: NavController, card: CardItemEntity, cardsViewModel: Cards_ViewModel) {
    var name by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }

    name = card.name
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate = Instant.ofEpochMilli(card.payDate).atZone(ZoneId.systemDefault()).toLocalDate()
    paymentDate = localDate.format(formatter)
    val localDate2 = Instant.ofEpochMilli(card.expiryDate).atZone(ZoneId.systemDefault()).toLocalDate()
    expiryDate = localDate2.format(formatter)
    balance = card.balance.toString()

    Scaffold(
        topBar = { AppBarTop(title = "Editar tarjeta", navController = navController)},
        bottomBar = { AppBarBottom(navController = navController) }
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

            item {
                Button(
                    onClick = {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val localDate = LocalDate.parse(paymentDate, formatter)
                        val timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        val formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val localDate2 = LocalDate.parse(expiryDate, formatter2)
                        val timestamp2 = localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        val newCard = CardItemEntity(id = card.id,
                            name = name, payDate = timestamp,
                            expiryDate = timestamp2,
                            balance = balance.toDouble())

                        cardsViewModel.updateCard(newCard)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Actualizar tarjeta")
                }
            }

            item { // Botón de eliminar
                Button(
                    onClick = {
                        cardsViewModel.deleteCard(card)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Eliminar tarjeta")
                }
            }
        }
    }
}
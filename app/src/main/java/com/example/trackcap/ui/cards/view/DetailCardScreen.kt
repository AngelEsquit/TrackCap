package com.example.trackcap.ui.cards.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import java.time.Instant
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailCardScreen(navController: NavController, cardsViewModel: Cards_ViewModel) {
    val card = cardsViewModel.selectedCard.value

    var balance by remember {
        mutableStateOf(card?.balance ?: 0.0)
    }
    var amountToDiscount by remember { mutableStateOf("0") }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val payDate = Instant.ofEpochMilli(card?.payDate ?: 0).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
    val expiryDate = Instant.ofEpochMilli(card?.expiryDate ?: 0).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
    val payDateString = payDate.format(formatter)
    val expiryDateString = expiryDate.format(formatter)

    Scaffold (
        topBar = { AppBarTop(title = "Detalle de tarjeta", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn (
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Card {
                    Column (modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()) {
                        Image(painter = painterResource(
                            id = com.example.trackcap.R.drawable.img_card),
                            contentDescription = "Tarjeta de crédito",
                            modifier = Modifier
                                .padding(bottom = 26.dp, top = 10.dp)
                                .fillMaxWidth()
                        )
                        Text("Nombre de la tarjeta: ${card?.name}")
                        Text("Monto a pagar: $balance")
                        Text("Fecha de pago: $payDateString")
                        Text("Fecha de vencimiento: $expiryDateString")

                        Button(onClick = {
                            if (card != null) {
                                cardsViewModel.selectEditCard(card)
                            }
                            navController.navigate(NavigationState.EditCard.route)
                        }, modifier = Modifier.padding(top = 16.dp)) {
                            Text("Editar")
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = amountToDiscount,
                                onValueChange = {
                                    amountToDiscount = it
                                },
                                label = { Text("Monto a descontar") },
                                placeholder = { Text("Escribe aquí...") },
                                isError = amountToDiscount.toIntOrNull() ?: 0 < 0,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .weight(1f)
                            )

                            Button(
                                onClick = {
                                    val discount = amountToDiscount.toIntOrNull() ?: 0
                                    if (discount > 0) {
                                        balance -= discount
                                        cardsViewModel.updateBalance(card!!.id, balance)
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 8.dp)
                                    .weight(1f)
                            ) {
                                Text("Descontar")
                            }
                        }
                    }
                }
            }
        }
    }
}
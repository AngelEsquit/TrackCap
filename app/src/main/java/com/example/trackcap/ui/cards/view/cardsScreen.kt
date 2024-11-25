package com.example.trackcap.ui.cards.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.R
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.cards.viewModel.Card
import com.example.trackcap.ui.cards.viewModel.CardsViewModel
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CardsScreen(navController: NavController, viewModel: CardsViewModel) {
    val cards by viewModel.cards.collectAsState()

    Scaffold(
        topBar = { AppBarTop(title = "Tarjetas", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Button(
                    onClick = { navController.navigate("addCard") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.tertiaryContainer)
                ) {
                    Text(text = "Agregar tarjeta", color = Color.Black)
                }
            }

            items(cards) { card ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("editCard/${card.name}") }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.img_card), contentDescription = null)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp)
                        ) {
                            Text(text = card.name)
                            Text(text = "Vence: ${card.expiryDate}")
                            Text(text = "Pago: ${card.paymentDate}")
                            Text(text = "Gastado este Mes: Q ${card.spentThisMonth}")
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun AddCardScreen(navController: NavController, onAddCard: (Card) -> Unit) {
    var name by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var spentThisMonth by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val paymentDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            paymentDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val expiryDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            expiryDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = { AppBarTop(title = "Agregar Tarjeta", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la Tarjeta") }
            )
            TextField(
                value = paymentDate,
                onValueChange = { paymentDate = it },
                label = { Text("Fecha de Pago") },
                modifier = Modifier.clickable { paymentDatePickerDialog.show() },
                readOnly = true
            )
            TextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Fecha de Vencimiento") },
                modifier = Modifier.clickable { expiryDatePickerDialog.show() },
                readOnly = true
            )
            TextField(
                value = spentThisMonth.toString(),
                onValueChange = { spentThisMonth = it.toIntOrNull() ?: 0 },
                label = { Text("Gastado este Mes") }
            )
            Button(
                onClick = {
                    val newCard = Card(name, paymentDate, expiryDate, spentThisMonth)
                    onAddCard(newCard)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Tarjeta")
            }
        }
    }
}

@Composable
fun EditCardScreen(navController: NavController, card: Card, onUpdateCard: (Card) -> Unit, onDeleteCard: (Card) -> Unit) {
    var name by remember { mutableStateOf(card.name) }
    var paymentDate by remember { mutableStateOf(card.paymentDate) }
    var expiryDate by remember { mutableStateOf(card.expiryDate) }
    var spentThisMonth by remember { mutableStateOf(card.spentThisMonth) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val paymentDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            paymentDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val expiryDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            expiryDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = { AppBarTop(title = "Editar Tarjeta", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la Tarjeta") }
            )
            TextField(
                value = paymentDate,
                onValueChange = { paymentDate = it },
                label = { Text("Fecha de Pago") },
                modifier = Modifier.clickable { paymentDatePickerDialog.show() },
                readOnly = true
            )
            TextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Fecha de Vencimiento") },
                modifier = Modifier.clickable { expiryDatePickerDialog.show() },
                readOnly = true
            )
            TextField(
                value = spentThisMonth.toString(),
                onValueChange = { spentThisMonth = it.toIntOrNull() ?: 0 },
                label = { Text("Gastado este Mes") }
            )
            Button(
                onClick = {
                    val updatedCard = card.copy(name = name, paymentDate = paymentDate, expiryDate = expiryDate, spentThisMonth = spentThisMonth)
                    onUpdateCard(updatedCard)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Tarjeta")
            }
            Button(
                onClick = {
                    onDeleteCard(card)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Eliminar Tarjeta")
            }
        }
    }
}
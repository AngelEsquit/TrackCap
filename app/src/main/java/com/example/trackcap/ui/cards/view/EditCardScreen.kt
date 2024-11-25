package com.example.trackcap.ui.cards.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.cards.viewModel.Card
import java.util.Calendar

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
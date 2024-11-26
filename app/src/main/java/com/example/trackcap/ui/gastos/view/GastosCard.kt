package com.example.trackcap.ui.gastos.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import java.time.Instant
import java.time.format.DateTimeFormatter

@Composable
fun GastosCard(navController: NavController, gastosViewModel: GastosViewModel, cardsViewmodel: Cards_ViewModel) {
    LaunchedEffect(Unit) {
        cardsViewmodel.selectedCard.value?.id?.let { cardId ->
            gastosViewModel.getItemsByCardId(cardId)
        }
    }

    val gastosCard = gastosViewModel.gastosByCardId.observeAsState()

    Scaffold(
        topBar = { AppBarTop(title = "Gastos de tarjeta", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Nombre",
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Categoria",
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Monto",
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Fecha",
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Eliminar",
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }

            val gastos = gastosCard.value ?: emptyList()

            items(gastos) { gasto ->
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val date = Instant.ofEpochMilli(gasto.date).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                val formattedDate = date.format(formatter)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = gasto.name,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 14.sp
                    )
                    Text(
                        text = gasto.category,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 14.sp
                    )
                    Text(
                        text = gasto.amount.toString(),
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 14.sp
                    )
                    Text(
                        text = formattedDate,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 14.sp
                    )
                    Button(
                        onClick = {
                            gastosViewModel.deleteItem(gasto)
                            cardsViewmodel.selectedCard.value?.id?.let { cardId ->
                                gastosViewModel.calculateCardBalance(cardId)
                                cardsViewmodel.updateBalance(cardId, gastosViewModel.balance.value ?: 0.0)
                                gastosViewModel.getItemsByCardId(cardId) // Update the list after deletion
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    ) {
                    }
                }

                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GastosCardPreview() {
    val gastosViewModel = viewModel(GastosViewModel::class.java)
    val cardsViewmodel = viewModel(modelClass = Cards_ViewModel::class.java)
    val navController = rememberNavController()

    GastosCard(navController = navController, gastosViewModel = gastosViewModel, cardsViewmodel = cardsViewmodel)
}
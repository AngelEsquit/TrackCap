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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.R
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectCardScreen(navController: NavController, cardsViewModel: Cards_ViewModel, gastosViewModel: GastosViewModel) {
    val cards = cardsViewModel.cards.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        cardsViewModel.getAllItems()
    }

    Scaffold(
        topBar = { AppBarTop(title = "Seleccionar tarjeta", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(cards.value) { card ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable {
                            val gasto = GastoItemEntity(
                                name = gastosViewModel.gasto.value!!.name,
                                category = gastosViewModel.gasto.value!!.category,
                                amount = gastosViewModel.gasto.value!!.amount,
                                date = gastosViewModel.gasto.value!!.date,
                                paymentMethod = gastosViewModel.gasto.value!!.paymentMethod,
                                cardId = card.id)

                            gastosViewModel.insertItem(gasto)

                            navigateTo(navController, NavigationState.Gastos.route, NavigationState.SelectCard.route)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_card),
                            contentDescription = "Card icon",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Column {
                            Text(text = card.name)
                        }
                    }
                }
            }
        }
    }
}
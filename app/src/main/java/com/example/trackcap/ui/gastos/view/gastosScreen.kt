package com.example.trackcap.ui.gastos.view

import android.annotation.SuppressLint
import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBar
import com.example.trackcap.ui.charts.view.ringChart
import com.example.trackcap.ui.common.view.floatingBotton
import com.example.trackcap.ui.common.view.tempSelector

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun gastosScreen(navController: NavController) {
    val categorias = listOf(Pair("Comida", 100f), Pair("Transporte", 200f),
        Pair("Entretenimiento", 150f), Pair("Salud", 75f),
        Pair("Educación", 50f), Pair("Otros", 25f))

    Scaffold (
        topBar = { AppBar(title = "Gastos", navController = navController) },
        floatingActionButton = { floatingBotton() }
    ) { innerPadding ->
        LazyColumn (
            contentPadding = innerPadding,
            modifier = Modifier
                .padding(16.dp)
        ){
            item { // Temporalidad
                tempSelector {}
            }

            item { // Gráfico
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)) {
                    ringChart(categorias)
                }
            }

            item { // Texto
                Row (modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = "Categorías")
                }
            }

            item { // Categorías
                Column (modifier = Modifier
                    .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    categorias.chunked(3).forEach { row ->
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            row.forEach { categoria ->
                                Card (modifier = Modifier
                                    .weight(1f)) {
                                    Row (modifier = Modifier
                                        .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center) {
                                        Text(text = categoria.first,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun gastosScreenPreview() {
    val navController = rememberNavController()
    gastosScreen(navController = navController)
}
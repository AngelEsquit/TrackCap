package com.example.trackcap.ui.gastos.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.ui.charts.view.ringChart
import com.example.trackcap.ui.common.view.floatingButton
import com.example.trackcap.ui.common.view.listSelector
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GastosScreen(navController: NavController, gastosViewModel: GastosViewModel) {

    LaunchedEffect(Unit) {
        val now = LocalDate.now()
        val startOfLastMonth = now.minusMonths(1).withDayOfMonth(1)
        val startOfLastMonthMillis = Date.from(startOfLastMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()).time

        gastosViewModel.getAllItems()
        gastosViewModel.getItemsByDate(startOfLastMonthMillis)
    }

    val color = colorScheme.tertiaryContainer
    val gastosByDate = gastosViewModel.gastosByDate.observeAsState(emptyList())

    Scaffold (
        topBar = { AppBarTop(title = "Gastos", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) },
        floatingActionButton = { floatingButton(navController = navController, route = NavigationState.AddGasto) }
    ) { innerPadding ->
        LazyColumn (
            contentPadding = innerPadding,
            modifier = Modifier
                .padding(16.dp)
        ){
            item { // Temporalidad
                val temporalidades = listOf("Día", "Semana", "Mes", "Año")
                listSelector(
                    label = "Temporalidad",
                    options = temporalidades,
                    onSelected = { temporalidad ->
                        val now = LocalDate.now()
                        val startOfPeriod = when (temporalidad) {
                            "Día" -> now
                            "Semana" -> now.minusWeeks(1).withDayOfMonth(1)
                            "Mes" -> now.minusMonths(1).withDayOfMonth(1)
                            "Año" -> now.minusYears(1).withDayOfMonth(1)
                            else -> now
                        }
                        val startOfPeriodMillis = Date.from(startOfPeriod.atStartOfDay(ZoneId.systemDefault()).toInstant()).time
                        gastosViewModel.getItemsByDate(startOfPeriodMillis)
                    },
                    defaultValue = "Mes"
                )
            }

            item { // Gráfico
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)) {
                    ringChart(gastosByDate.value)
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
                    gastosByDate.value.chunked(3).forEach { row ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            row.forEach { categoria ->
                                Card (modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)

                                ) {
                                    Box (modifier = Modifier
                                        .background(color = color)
                                        .padding(8.dp)
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)


                                    ) {
                                        Text(text = categoria.first,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            color = Color.Black)
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
    val gastosViewModel = viewModel(GastosViewModel::class.java)
    GastosScreen(navController = navController, gastosViewModel = gastosViewModel)
}
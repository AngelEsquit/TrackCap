package com.example.trackcap.ui.cards.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.navigation.navigateTo

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun homeScreen(navController: NavController) {
    val color0 = colorScheme.surfaceContainerLowest
    val color1 = colorScheme.outlineVariant
    val color2 = colorScheme.outline
    val color3 = colorScheme.tertiaryContainer
    val saldo by remember { mutableStateOf(0.00) }
    val movimientos = remember { mutableStateOf(listOf(Triple("Gasto 1", 10.00, color1), Triple("Ingreso 1", 100.00, color2), Triple("Gasto 2", 75.00, color1))) }
    val activos = remember {
        mutableStateOf(listOf(Triple("Activo 1", 100.00, color1), Triple("Activo 2", 200.00, color2), Triple("Activo 3", 150.00, color1)))
    }

    Scaffold (
        topBar = { AppBarTop(title = "TrackCap", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn (
            contentPadding = innerPadding
        ) {
            item {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center) {
                    Column (modifier = Modifier
                        .wrapContentSize(Alignment.Center)) {
                        Text(text = "Saldo",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally))
                        Text(text = "Q " + "%.2f".format(saldo),
                            fontSize = 28.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }

            item {
                Row {
                    Text(text = "Últimos movimientos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp))
                }

                Column (modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Column {
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .background(color = color0)){
                            Text(text = "Movimiento",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f))
                            Text(text = "Monto",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f))
                        }


                        movimientos.value.forEach() { movimiento ->
                            Row (modifier = Modifier
                                .fillMaxWidth()
                                .background(movimiento.third)){
                                Text(
                                    text = movimiento.first,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
                                Text(
                                    text = "Q " + "%.2f".format(movimiento.second),
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row {
                    Text(text = "Activos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp))
                }

                Column (modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Column {
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .background(color = color0)){
                            Text(text = "Activo",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f))
                            Text(text = "Monto",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f))
                        }


                        activos.value.forEach() { activo ->
                            Row (modifier = Modifier
                                .fillMaxWidth()
                                .background(activo.third)){
                                Text(
                                    text = activo.first,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
                                Text(
                                    text = "Q " + "%.2f".format(activo.second),
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
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
fun homeScreenPreview() {
    val navController = rememberNavController()
    homeScreen(navController = navController)
}
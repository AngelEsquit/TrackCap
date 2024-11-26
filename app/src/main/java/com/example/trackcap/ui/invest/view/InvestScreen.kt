package com.example.trackcap.ui.invest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackcap.MyApp
import com.example.trackcap.R
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.invest.viewModel.InvestViewModel
import com.example.trackcap.ui.invest.repository.InvestRepository
import com.example.trackcap.ui.invest.viewModel.InvestViewModelFactory
import kotlin.random.Random

@Composable
fun InvestScreen(
    navController: NavController,
    viewModel: InvestViewModel = viewModel(factory = InvestViewModelFactory(InvestRepository((navController.context.applicationContext as MyApp).database.activoItemDao())))
) {
    val investments by viewModel.investments.collectAsState()

    Scaffold(
        topBar = { AppBarTop(title = "Activos", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Button(
                    onClick = { navController.navigate("addInvest") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.tertiaryContainer)
                ) {
                    Text(text = "Agregar activo", color = Color.Black)
                }
            }

            item {
                Row {
                    Text(
                        text = "Activos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                        ) {
                            Text(
                                text = "Activo",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                            )
                            Text(
                                text = "Monto Original",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                            )
                            Text(
                                text = "Monto Actual",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                            )
                            Text(
                                text = "Cambio",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                            )
                        }

                        investments.forEach { investment ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            ) {
                                Text(
                                    text = investment.name,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
                                Text(
                                    text = "$ " + "%.2f".format(investment.originalAmount),
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
                                Text(
                                    text = "$ " + "%.2f".format(investment.originalAmount),
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                )
                                val change = investment.originalAmount - investment.originalAmount
                                val changeIcon = when {
                                    change > 0 -> R.drawable.ic_arrow_up
                                    change < 0 -> R.drawable.ic_arrow_down
                                    else -> R.drawable.ic_arrow_right
                                }
                                val iconSize = if (changeIcon == R.drawable.ic_arrow_right) 16.dp else 24.dp
                                Image(
                                    painter = painterResource(id = changeIcon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(iconSize)
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
package com.example.trackcap.ui.ingresos.view

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModel
import java.time.Instant
import java.time.format.DateTimeFormatter

@Composable
fun IngresosListScreen(navController: NavController, ingresosViewModel: IngresosViewModel) {
    LaunchedEffect(Unit) {
        ingresosViewModel.getAllItems()
    }

    val ingresosCategory = ingresosViewModel.ingresosByCategory.observeAsState()

    Scaffold(
        topBar = { AppBarTop(title = "Ingresos de {${ingresosViewModel.selectedCategory.value}}", navController = navController) },
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

            val ingresos = ingresosCategory.value ?: emptyList()

            items(ingresos) { ingreso ->
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val date = Instant.ofEpochMilli(ingreso.date).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                val formattedDate = date.format(formatter)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = ingreso.name,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 14.sp
                    )
                    Text(
                        text = ingreso.category,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        fontSize = 14.sp
                    )
                    Text(
                        text = ingreso.amount.toString(),
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
                            ingresosViewModel.deleteItem(ingreso)
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
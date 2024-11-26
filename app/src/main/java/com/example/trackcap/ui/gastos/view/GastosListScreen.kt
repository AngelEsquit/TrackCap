package com.example.trackcap.ui.gastos.view

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
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import java.time.Instant
import java.time.format.DateTimeFormatter

@Composable
fun GastosListScreen(navController: NavController, GastosViewModel: GastosViewModel) {
    LaunchedEffect(Unit) {
        GastosViewModel.getAllItems()
        GastosViewModel.selectedCategory.value?.let { GastosViewModel.getGastosByCategory(it) }
    }

    val gastosCategory = GastosViewModel.gastosByCategory.observeAsState()

    Scaffold(
        topBar = { AppBarTop(title = "Gastos de ${GastosViewModel.selectedCategory.value}", navController = navController) },
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

            val gastos = gastosCategory.value ?: emptyList()

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
                            GastosViewModel.deleteItem(gasto)
                            GastosViewModel.selectedCategory.value?.let { GastosViewModel.getGastosByCategory(it) }
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
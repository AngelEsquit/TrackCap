package com.example.trackcap.ui.invest.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackcap.MyApp
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.ui.invest.repository.InvestRepository
import com.example.trackcap.ui.invest.viewModel.InvestViewModel
import com.example.trackcap.ui.invest.viewModel.InvestViewModelFactory
import com.example.trackcap.ui.invest.viewModel.Investment

@Composable
fun AddInvestScreen(
    navController: NavController,
    viewModel: InvestViewModel = viewModel(factory = InvestViewModelFactory(InvestRepository((navController.context.applicationContext as MyApp).database.activoItemDao())))
) {
    var investmentName by remember { mutableStateOf("") }
    var originalAmount by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    val suggestions by viewModel.suggestions.collectAsState()

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { AppBarTop(title = "Añadir activo", navController = navController) },
        bottomBar = { AppBarBottom(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                TextField(
                    value = investmentName,
                    onValueChange = {
                        investmentName = it
                        if (it.isNotEmpty()) {
                            viewModel.searchInvestments(it)
                        }
                    },
                    label = { Text("Nombre del activo") },
                    placeholder = { Text("Escribe aquí...") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
                suggestions.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                investmentName = suggestion
                                viewModel.searchInvestments("") // Clear suggestions
                            }
                    )
                }
            }

            item {
                TextField(
                    value = originalAmount,
                    onValueChange = { originalAmount = it },
                    label = { Text("Monto original (USD)") },
                    placeholder = { Text("Escribe aquí...") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true
                )
            }

            item {
                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad de activo") },
                    placeholder = { Text("Escribe aquí...") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    singleLine = true
                )
            }

            item {
                Button(
                    onClick = {
                        val investment = Investment(investmentName, originalAmount.toDouble(), 0.0, quantity.toDouble())
                        viewModel.addInvestment(investment)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Agregar inversión")
                }
            }
        }
    }
}
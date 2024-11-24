package com.example.trackcap.ui.invest.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.AppBarBottom
import com.example.trackcap.navigation.AppBarTop
import com.example.trackcap.networking.RetrofitClient
import com.example.trackcap.networking.response.SearchResponse
import com.example.trackcap.ui.invest.viewModel.Investment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AddInvestScreen(navController: NavController) {
    var investmentName by remember { mutableStateOf("") }
    var originalAmount by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(listOf<String>()) }

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
                            searchInvestments(it) { results: List<String> ->
                                suggestions = results
                            }
                        } else {
                            suggestions = emptyList()
                        }
                    },
                    label = { Text("Nombre del activo") },
                    placeholder = { Text("Escribe aquí...") },
                    singleLine = true
                )
                suggestions.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                investmentName = suggestion
                                suggestions = emptyList()
                            }
                    )
                }
            }

            item {
                TextField(
                    value = originalAmount,
                    onValueChange = { originalAmount = it },
                    label = { Text("Monto original") },
                    placeholder = { Text("Escribe aquí...") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            item {
                Button(
                    onClick = {
                        val investment = Investment(investmentName, originalAmount.toDouble(), 0.0)
                        // Add investment to the list and navigate back
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

fun searchInvestments(query: String, onResult: (List<String>) -> Unit) {
    val call = RetrofitClient.instance.searchInvestments("SYMBOL_SEARCH", query, "YOUR_API_KEY")
    call.enqueue(object : Callback<SearchResponse> {
        override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
            if (response.isSuccessful) {
                val results = response.body()?.bestMatches?.map { it.symbol } ?: emptyList()
                onResult(results)
            }
        }

        override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            onResult(emptyList())
        }
    })
}
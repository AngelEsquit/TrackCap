package com.example.trackcap.ui.invest.repository

import android.util.Log
import com.example.trackcap.networking.RetrofitClient
import com.example.trackcap.networking.response.SearchResponse
import com.example.trackcap.networking.response.StockResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvestRepository {
    fun fetchCurrentAmount(symbol: String, onResult: (Double) -> Unit) {
        val call = RetrofitClient.instance.getStockData("TIME_SERIES_INTRADAY", symbol, "YOUR_API_KEY")
        call.enqueue(object : Callback<StockResponse> {
            override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                if (response.isSuccessful) {
                    val stockData = response.body()?.timeSeries?.values?.firstOrNull()
                    val currentAmount = stockData?.close?.toDoubleOrNull() ?: 0.0
                    Log.d("InvestRepository", "Current amount: $currentAmount") // Agregar log de depuración
                    onResult(currentAmount)
                } else {
                    Log.d("InvestRepository", "Error in response: ${response.message()}")
                    onResult(0.0)
                }
            }

            override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                Log.d("InvestRepository", "Failure: ${t.message}")
                onResult(0.0)
            }
        })
    }

    fun searchInvestments(query: String, onResult: (List<String>) -> Unit) {
        val call = RetrofitClient.instance.searchInvestments("SYMBOL_SEARCH", query, "YOUR_API_KEY")
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.bestMatches?.map { it.symbol } ?: emptyList()
                    Log.d("InvestRepository", "Search results: $results") // Agregar log de depuración
                    onResult(results)
                } else {
                    Log.d("InvestRepository", "Error in response: ${response.message()}")
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("InvestRepository", "Failure: ${t.message}")
                onResult(emptyList())
            }
        })
    }
}

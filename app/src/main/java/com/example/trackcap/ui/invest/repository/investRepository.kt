package com.example.trackcap.ui.invest.repository

import com.example.trackcap.database.invest.ActivoItemDao
import com.example.trackcap.database.invest.ActivoItemEntity
import com.example.trackcap.networking.RetrofitClient
import com.example.trackcap.networking.response.TwelveDataSearchResponse
import com.example.trackcap.networking.response.TwelveDataStockResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvestRepository(private val activoItemDao: ActivoItemDao) {

    suspend fun insertInvestment(investment: ActivoItemEntity) {
        withContext(Dispatchers.IO) {
            activoItemDao.insert(investment)
        }
    }

    suspend fun getAllInvestments(): List<ActivoItemEntity> {
        return withContext(Dispatchers.IO) {
            activoItemDao.getAllItems()
        }
    }

    fun fetchCurrentAmount(symbol: String, onResult: (Double) -> Unit) {
        val call = RetrofitClient.instance.getStockData(
            symbol,
            "1min",
            "d9eb2203d216430b874c7505fe576f10"
        )

        call.enqueue(object : Callback<TwelveDataStockResponse> {
            override fun onResponse(call: Call<TwelveDataStockResponse>, response: Response<TwelveDataStockResponse>) {
                if (response.isSuccessful) {
                    val stockData = response.body()?.timeSeries?.firstOrNull()
                    val priceInUSD = stockData?.close?.toDoubleOrNull() ?: 0.0
                    onResult(priceInUSD)
                } else {
                    onResult(0.0)
                }
            }

            override fun onFailure(call: Call<TwelveDataStockResponse>, t: Throwable) {
                onResult(0.0)
            }
        })
    }

    fun searchInvestments(query: String, onResult: (List<String>) -> Unit) {
        val call = RetrofitClient.instance.searchInvestments(query, "d9eb2203d216430b874c7505fe576f10")
        call.enqueue(object : Callback<TwelveDataSearchResponse> {
            override fun onResponse(call: Call<TwelveDataSearchResponse>, response: Response<TwelveDataSearchResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.bestMatches?.map { it.symbol } ?: emptyList()
                    onResult(results)
                } else {
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<TwelveDataSearchResponse>, t: Throwable) {
                onResult(emptyList())
            }
        })
    }
}
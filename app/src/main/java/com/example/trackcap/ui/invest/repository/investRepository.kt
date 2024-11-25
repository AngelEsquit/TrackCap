package com.example.trackcap.ui.invest.repository

import com.example.trackcap.BuildConfig
import com.example.trackcap.database.invest.ActivoItemDao
import com.example.trackcap.database.invest.ActivoItemEntity
import com.example.trackcap.networking.RetrofitClient
import com.example.trackcap.networking.response.ExchangeRateResponse
import com.example.trackcap.networking.response.SearchResponse
import com.example.trackcap.networking.response.StockResponse
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
            "TIME_SERIES_INTRADAY",
            symbol,
            "YOUR_API_KEY"
        )

        call.enqueue(object : Callback<StockResponse> {
            override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                if (response.isSuccessful) {
                    val stockData = response.body()?.timeSeries?.values?.firstOrNull()
                    val priceInUSD = stockData?.close?.toDoubleOrNull() ?: 0.0

                    // Fetch USD to GTQ exchange rate
                    fetchUsdToGqtRate { exchangeRate ->
                        val priceInGTQ = priceInUSD * exchangeRate
                        onResult(priceInGTQ)
                    }
                } else {
                    onResult(0.0)
                }
            }

            override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                onResult(0.0)
            }
        })
    }

    private fun fetchUsdToGqtRate(onResult: (Double) -> Unit) {
        val call = RetrofitClient.instance.getExchangeRate("USD", "GTQ", "67XH0LBJTJNCNU82")

        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(
                call: Call<ExchangeRateResponse>,
                response: Response<ExchangeRateResponse>
            ) {
                if (response.isSuccessful) {
                    val rate = response.body()?.realtimeRate?.rate?.toDoubleOrNull() ?: 0.0
                    onResult(rate)
                } else {
                    onResult(0.0)
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                onResult(0.0)
            }
        })
    }


    fun searchInvestments(query: String, onResult: (List<String>) -> Unit) {
        val call = RetrofitClient.instance.searchInvestments("SYMBOL_SEARCH", query, BuildConfig.ALPHA_VANTAGE_API_KEY)
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.bestMatches?.map { it.symbol } ?: emptyList()
                    onResult(results)
                } else {
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                onResult(emptyList())
            }
        })
    }
}
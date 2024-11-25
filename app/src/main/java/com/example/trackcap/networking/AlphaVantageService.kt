package com.example.trackcap.networking

import com.example.trackcap.networking.response.ExchangeRateResponse
import com.example.trackcap.networking.response.SearchResponse
import com.example.trackcap.networking.response.StockResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageService {
    @GET("query")
    fun getStockData(
        @Query("function") function: String,
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Call<StockResponse>

    @GET("query")
    fun searchInvestments(
        @Query("function") function: String,
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String
    ): Call<SearchResponse>

    @GET("query")
    fun getExchangeRate(
        @Query("from_currency") fromCurrency: String,
        @Query("to_currency") toCurrency: String,
        @Query("apikey") apiKey: String
    ): Call<ExchangeRateResponse>
}
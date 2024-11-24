package com.example.trackcap.networking.response

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
}
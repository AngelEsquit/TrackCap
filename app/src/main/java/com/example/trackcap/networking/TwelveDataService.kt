package com.example.trackcap.networking

import com.example.trackcap.networking.response.TwelveDataSearchResponse
import com.example.trackcap.networking.response.TwelveDataStockResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TwelveDataService {
    @GET("time_series")
    fun getStockData(
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "1min",
        @Query("apikey") apiKey: String
    ): Call<TwelveDataStockResponse>

    @GET("symbol_search")
    fun searchInvestments(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Call<TwelveDataSearchResponse>
}
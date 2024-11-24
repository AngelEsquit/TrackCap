package com.example.trackcap.networking

import com.example.trackcap.networking.response.AlphaVantageService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.alphavantage.co/"

    val instance: AlphaVantageService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlphaVantageService::class.java)
    }
}

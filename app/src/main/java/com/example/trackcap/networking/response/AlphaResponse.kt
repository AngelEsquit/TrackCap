package com.example.trackcap.networking.response

import com.google.gson.annotations.SerializedName


data class MetaData(
    @SerializedName("1. Information") val information: String,
    @SerializedName("2. Symbol") val symbol: String
)


data class SearchResponse(
    @SerializedName("bestMatches") val bestMatches: List<BestMatch>
)

data class BestMatch(
    @SerializedName("1. symbol") val symbol: String,
    @SerializedName("2. name") val name: String
)

fun SearchResponse.toSymbols(): List<String> {
    return this.bestMatches.map { it.symbol }
}

fun StockResponse.toCurrentAmount(): Double {
    val stockData = this.timeSeries?.values?.firstOrNull()
    return stockData?.close?.toDoubleOrNull() ?: 0.0
}


data class StockResponse(
    @SerializedName("Time Series (1min)")
    val timeSeries: Map<String, StockData>?
)

data class StockData(
    @SerializedName("4. close")
    val close: String
)

data class ExchangeRateResponse(
    @SerializedName("Realtime Currency Exchange Rate")
    val realtimeRate: ExchangeRateData?
)

data class ExchangeRateData(
    @SerializedName("5. Exchange Rate")
    val rate: String
)

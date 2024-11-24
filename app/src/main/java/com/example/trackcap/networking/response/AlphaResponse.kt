package com.example.trackcap.networking.response

import com.google.gson.annotations.SerializedName

data class StockResponse(
    @SerializedName("Meta Data") val metaData: MetaData,
    @SerializedName("Time Series (5min)") val timeSeries: Map<String, StockData>
)

data class MetaData(
    @SerializedName("1. Information") val information: String,
    @SerializedName("2. Symbol") val symbol: String
)

data class StockData(
    @SerializedName("1. open") val open: String,
    @SerializedName("2. high") val high: String,
    @SerializedName("3. low") val low: String,
    @SerializedName("4. close") val close: String,
    @SerializedName("5. volume") val volume: String
)
data class SearchResponse(
    @SerializedName("bestMatches") val bestMatches: List<BestMatch>
)

data class BestMatch(
    @SerializedName("1. symbol") val symbol: String,
    @SerializedName("2. name") val name: String
)
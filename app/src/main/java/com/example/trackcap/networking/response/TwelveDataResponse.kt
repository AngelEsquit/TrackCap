package com.example.trackcap.networking.response

import com.google.gson.annotations.SerializedName

data class TwelveDataSearchResponse(
    @SerializedName("data") val bestMatches: List<BestMatch>
)

data class BestMatch(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String
)

data class TwelveDataStockResponse(
    @SerializedName("values") val timeSeries: List<StockData>
)

data class StockData(
    @SerializedName("close") val close: String
)
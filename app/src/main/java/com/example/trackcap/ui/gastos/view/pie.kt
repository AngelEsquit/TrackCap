package com.example.trackcap.ui.gastos.view

import androidx.compose.runtime.Composable
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PieChart(data: List<Pair<String, Float>>) {
    val entries = data.map { PieEntry(it.second, it.first) }
    val dataSet = PieDataSet(entries, "Categorías")

    val pieData = PieData(dataSet)
    val pieChart = PieChart(LocalContext.current)

    AndroidView(
        factory = { pieChart },
        update = { view ->
            view.data = pieData
            view.invalidate() // Refresca el gráfico
        }
    )
}
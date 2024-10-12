package com.example.trackcap.ui.gastos.view

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun PieChartView(data: List<Pair<String, Float>>) {
    val context = LocalContext.current

    AndroidView(
        factory = {PieChart(context).apply {
            description.isEnabled = false
            setUsePercentValues(true)
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.Black.toArgb())
            centerText = "Gastos por Categoría"
            setCenterTextSize(24f)
            isDrawHoleEnabled = true
            holeRadius = 0f
            transparentCircleRadius = 0f
            setDrawCenterText(true)
            legend.isEnabled = true
            // ... otras configuraciones del gráfico ...
        }
        },
        update = { pieChart ->
            val entries = data.map { PieEntry(it.second, it.first) }
            val dataSet = PieDataSet(entries, "Categorías").apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
                // ... otras configuraciones del dataSet ...
            }

            val pieData = PieData(dataSet)
            pieChart.data = pieData
            pieChart.invalidate() // Refresca el gráfico
        },
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
    )
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    val data = listOf(
        "Comida" to 100f,
        "Transporte" to 200f,
        "Entretenimiento" to 150f,
        "Otros" to 50f
    )
    PieChartView(data)
}
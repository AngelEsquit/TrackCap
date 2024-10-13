package com.example.trackcap.ui.charts.view

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun ringChart(data: List<Pair<String, Float>>) {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                isDrawHoleEnabled = true // Habilita el agujero central
                holeRadius = 50f // Ajusta el radio del agujero (en porcentaje del radio del gráfico)
                description.isEnabled = false // Deshabilita la descripción
                legend.isEnabled = false // Deshabilita la leyenda
                legend.horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
                setUsePercentValues(true)
            }
        },
        update = { pieChart ->
            val entries = data.map { PieEntry(it.second, it.first) }
            val dataSet = PieDataSet(entries, "Categories").apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
                valueFormatter = PercentFormatter()
                xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
                yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

                valueTextSize = 15f
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
fun ringChartPreview() {
    val data = listOf(
        "Comida" to 100f,
        "Transporte" to 200f,
        "Entretenimiento" to 150f,
        "Salud" to 75f,
        "Educación" to 80f,
        "Otros" to 50f
    )
    ringChart(data)
}
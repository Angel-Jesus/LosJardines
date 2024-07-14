package com.angelpr.losjardines.ui.recycleView

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.StatisticsModel
import com.angelpr.losjardines.databinding.ItemBarcharBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class StatisticsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemBarcharBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(statisticsData: StatisticsModel.Description, month: String ,position: Int) {

        when(position) {
            0 -> binding.staristicsTitle.text = "Estadistica por dia del mes de $month"
            1 -> binding.staristicsTitle.text = "Estadistica por habitacion del mes de $month"
            2 -> binding.staristicsTitle.text = "Estadistica por procedencia del mes de $month"
        }

        binding.charBar.description.isEnabled = false
        binding.charBar.axisRight.setDrawLabels(false)

        val xAxis = binding.charBar.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(statisticsData.label)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.axisLineColor = R.color.black
        xAxis.axisLineWidth = 2f
        xAxis.labelCount = statisticsData.label.size
        xAxis.labelRotationAngle = 0f

        val yAxis = binding.charBar.axisLeft
        yAxis.mAxisMaximum = (statisticsData.maxValue + 10).toFloat()
        yAxis.mAxisMinimum = 0f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = R.color.black
        yAxis.setDrawGridLines(false)
        yAxis.setDrawLabels(true)

        val legend = binding.charBar.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT

        val dataSet = BarDataSet(statisticsData.entrie, "Cantidad de clientes")

        dataSet.valueTextSize = 12f
        dataSet.setColor(Color.parseColor("#BF272D"))
        dataSet.setDrawValues(true)

        val dataBar = BarData(dataSet)

        binding.charBar.data = dataBar
        binding.charBar.invalidate()
    }
}
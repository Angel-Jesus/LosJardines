package com.angelpr.losjardines.data.model

import com.github.mikephil.charting.data.BarEntry

data class StatisticsModel(
    var month: String = "",
    var statisticsData: List<Description> = emptyList(),
) {
    data class Description(val maxValue: Int, val entrie: List<BarEntry>, val label: List<String>)
}




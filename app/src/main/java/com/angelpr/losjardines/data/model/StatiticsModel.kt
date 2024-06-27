package com.angelpr.losjardines.data.model

data class StatiticsModel(
    var statisticsDays: List<Description> = emptyList(),
    var statisticsRoom: List<Description> = emptyList(),
    var statisticsCity: List<Description> = emptyList()
) {
    data class Description(val description: String, val value: Int)
}




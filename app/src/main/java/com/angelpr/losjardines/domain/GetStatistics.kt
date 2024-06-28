package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.StatisticsProvider
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.StatisticsModel

class GetStatistics {
    private val statisticsProvider = StatisticsProvider()

    operator fun invoke(month:String, clientsRegister: List<ClientInfoModel>): StatisticsModel =
        statisticsProvider.getStatistics(month, clientsRegister)
}
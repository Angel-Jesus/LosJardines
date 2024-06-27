package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.StatisticsProvider
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.StatiticsModel

class GetStatistics {
    private val statisticsProvider = StatisticsProvider()

    operator fun invoke(clientsRegister: List<ClientInfoModel>): StatiticsModel =
        statisticsProvider.getStatistics(clientsRegister)
}
package com.angelpr.losjardines.data.model

import com.angelpr.losjardines.data.model.types.FilterType
import com.angelpr.losjardines.data.model.types.Months

data class ClientsRegisterModel(
    var clientsList: List<ClientInfoModel> = emptyList(),
    val filter: FilterType = FilterType.Default,
    val descriptionFilter: String = "",
    val timeFilter: Months = Months.NONE
)

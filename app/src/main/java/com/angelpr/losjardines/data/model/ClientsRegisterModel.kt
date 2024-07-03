package com.angelpr.losjardines.data.model

data class ClientsRegisterModel(
    var clientsList: List<ClientInfoModel> = emptyList(),
    val filter: FilterType = FilterType.Default,
    val descriptionFilter: String = "",
    val timeFilter: Months = Months.NONE
)

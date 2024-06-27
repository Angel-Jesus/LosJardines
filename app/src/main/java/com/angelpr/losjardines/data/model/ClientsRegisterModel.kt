package com.angelpr.losjardines.data.model

data class ClientsRegisterModel(
    var clientsList: List<ClientInfoModel> = emptyList(),
    var loading: Boolean = true,
    val filter: FilterType = FilterType.Default,
    val descriptionFilter: String = "",
    val timeFilter: Months = Months.NONE
)

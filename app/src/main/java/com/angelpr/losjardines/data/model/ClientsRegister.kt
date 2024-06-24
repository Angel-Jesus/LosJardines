package com.angelpr.losjardines.data.model

data class ClientsRegister(
    var clientsList: List<Client> = emptyList(),
    var loading: Boolean = true,
    val filter: FilterType = FilterType.Default,
    val descriptionFilter: String = "",
    val timeFilter: Months = Months.NONE
)

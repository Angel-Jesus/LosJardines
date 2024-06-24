package com.angelpr.losjardines.data.model

sealed interface FilterType {
    data object Default: FilterType
    data object Mont: FilterType
    data object Origin: FilterType
    data object Dni: FilterType
    data object lastMonth: FilterType
}


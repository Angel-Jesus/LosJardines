package com.angelpr.losjardines.data.model.types

object SpinnerItem {
    val SPINNER_MONTH = listOf(
        "Seleccionar mes",
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre"
    )

    val SPINNER_FILTER = listOf("Aplicar Filtro", "Procedencia", "Mes", "DNI")

    enum class SpinnerPosition {
        None,
        Origin,
        Month,
        DNI
    }
}
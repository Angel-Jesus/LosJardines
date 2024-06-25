package com.angelpr.losjardines.data.model

data class UpdateData(
    val collection: String,
    val documentPath: String,
    val keyField: String,
    val data: Any,
)
package com.angelpr.losjardines.data.model

data class UpdateDataModel(
    val collection: String,
    val documentPath: String,
    val keyField: String,
    var data: Any,
)

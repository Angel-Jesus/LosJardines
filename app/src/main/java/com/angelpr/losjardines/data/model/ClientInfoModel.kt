package com.angelpr.losjardines.data.model

data class ClientInfoModel(
    var collection: String,
    var id: String = "",
    var name: String,
    var dni: String,
    var date: String,
    var hour: String,
    var observation: String,
    var price: Int,
    var origin: String,
    var room: Int
)

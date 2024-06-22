package com.angelpr.losjardines.data.model

data class InfoClient(
    var id: String,
    var name: String,
    var dni: String,
    var date: String,
    var time: String,
    var observation: String,
    var price: Int,
    var origin: String,
    var room: Int
)

package com.angelpr.losjardines.data.model

data class ReservationModel(
    val room: Int,
    val dateReservation: String,
    val dateEnter: String,
    val dateExit: String,
    val numberNight: Int,
    val numberPassenger: Int,
    val typeService: String,
    val name: String,
    val dni: String,
    val nationality: String,
    val fee: String,
    val phoneEmail: String,
    val observation: String = ""
)
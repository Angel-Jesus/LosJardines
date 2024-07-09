package com.angelpr.losjardines.data.model

import com.angelpr.losjardines.data.model.types.TopicReservation

data class ReservationModel(
    val description: TopicReservation = TopicReservation.LOGO,
    var valueText: String = ""
)
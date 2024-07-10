package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.ActionProcess

class GetReservationToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(callback: (List<ReservationModel>, ActionProcess) -> Unit) {
        firebaseAccess.getReservation{reservationList, response ->
            callback(reservationList, response)
        }
    }
}
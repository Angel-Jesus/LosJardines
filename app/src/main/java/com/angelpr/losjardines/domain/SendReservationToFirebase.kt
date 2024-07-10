package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.ActionProcess

class SendReservationToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(data: ReservationModel, callback: (ActionProcess) -> Unit) {
        firebaseAccess.sendReservation(data){ success ->
            callback(success)
        }
    }
}
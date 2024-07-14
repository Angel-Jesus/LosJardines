package com.angelpr.losjardines.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.Check
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.ActionProcess

@RequiresApi(Build.VERSION_CODES.O)
class SendReservationToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(
        data: ReservationModel,
        callback: (ActionProcess) -> Unit
    ) {
        firebaseAccess.sendReservation(data) { success ->
            callback(success)
        }
    }
}
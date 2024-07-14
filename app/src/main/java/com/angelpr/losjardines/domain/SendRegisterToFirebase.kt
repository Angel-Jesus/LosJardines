package com.angelpr.losjardines.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.Check
import com.angelpr.losjardines.data.model.types.ActionProcess
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ReservationModel

@RequiresApi(Build.VERSION_CODES.O)
class SendRegisterToFirebase {
    private val firebaseAccess = FirebaseAccess()
    private val check = Check()

    operator fun invoke(
        data: ClientInfoModel,
        reservation: List<ReservationModel>,
        callback: (ActionProcess) -> Unit
    ) {
        if (check.reservationNotExistRg(data, reservation)) {
            firebaseAccess.sendRegister(data){ success ->
                callback(success)
            }
        } else {
            callback(ActionProcess.NOT_AVAILABLE)
        }

    }
}
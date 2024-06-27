package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.ClientInfoModel

class SendDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(data: ClientInfoModel, callback: (Boolean) -> Unit) {
        firebaseAccess.sendRegister(data){ success ->
            callback(success)
        }
    }
}
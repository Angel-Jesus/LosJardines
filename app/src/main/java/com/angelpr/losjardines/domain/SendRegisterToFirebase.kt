package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.types.ActionProcess
import com.angelpr.losjardines.data.model.ClientInfoModel

class SendRegisterToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(data: ClientInfoModel, callback: (ActionProcess) -> Unit) {
        firebaseAccess.sendRegister(data){ success ->
            callback(success)
        }
    }
}
package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.FirebaseAccess
import com.angelpr.losjardines.data.model.Client

class SendDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(data: Client, callback: (Boolean) -> Unit) {
        firebaseAccess.sendRegister(data){ success ->
            callback(success)
        }
    }
}
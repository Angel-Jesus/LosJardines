package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.FirebaseAccess
import com.angelpr.losjardines.data.model.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(clienInfo: Client, callback: (Boolean) -> Unit) {
        firebaseAccess.sendRegister(clienInfo){ success ->
            callback(success)
        }
    }
}
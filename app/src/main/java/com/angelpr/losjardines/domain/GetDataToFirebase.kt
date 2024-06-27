package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.ClientsRegisterModel

class GetDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(clientsRegister: ClientsRegisterModel, callback: (ClientsRegisterModel) -> Unit){
        firebaseAccess.getRegister(clientsRegister){
            callback(it)
        }
    }
}
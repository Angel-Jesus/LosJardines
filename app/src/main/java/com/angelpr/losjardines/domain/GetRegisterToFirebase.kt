package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.types.ActionProcess
import com.angelpr.losjardines.data.model.ClientsRegisterModel

class GetRegisterToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(clientsRegister: ClientsRegisterModel, callback: (ClientsRegisterModel, ActionProcess) -> Unit){
        firebaseAccess.getRegister(clientsRegister){clientsRegisterModel, actionProcess ->
            callback(clientsRegisterModel, actionProcess)
        }
    }
}
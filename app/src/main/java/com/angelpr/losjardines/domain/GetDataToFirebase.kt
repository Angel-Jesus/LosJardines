package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.FirebaseAccess
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.data.model.FilterType

class GetDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(clientsRegister: ClientsRegister, callback: (ClientsRegister) -> Unit){
        firebaseAccess.getRegister(clientsRegister){
            callback(it)
        }
    }
}
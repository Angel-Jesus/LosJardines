package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.ActionProcess

class DeleteDataToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(collection: String, documentPath: String, callback: (ActionProcess) -> Unit){
        firebaseAccess.deleteRegister(collection = collection, documentPath = documentPath){response ->
            callback(response)
        }
    }
}
package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.types.ActionProcess

class UpdateDataToFirebase {
    private val firebaseAccess = FirebaseAccess()

    operator fun invoke(collection: String, documentPath: String, keyField: String, data: Any, callback: (ActionProcess) -> Unit){
        firebaseAccess.updateRegister(collection = collection, documentPath = documentPath, keyField = keyField, data = data){ response ->
            callback(response)
        }
    }
}
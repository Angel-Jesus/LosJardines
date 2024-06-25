package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.FirebaseAccess

class UpdateDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(collection: String, documentPath: String, keyField: String, data: Any, callback: (Boolean) -> Unit){
        firebaseAccess.updateRegister(collection = collection, documentPath = documentPath, keyField = keyField, data = data){ success ->
            callback(success)
        }
    }
}
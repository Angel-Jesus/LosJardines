package com.angelpr.losjardines.domain

import com.angelpr.losjardines.data.FirebaseAccess

class UpdateDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(collection: String, documentPath: String, keyField: String, updateData: Any, callback: (Boolean) -> Unit){
        firebaseAccess.updateRegister(collection = collection, documentPath = documentPath, key = keyField, data = updateData){ success ->
            callback(success)
        }
    }
}
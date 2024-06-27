package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess

class DeleteDataToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(collection: String, documentPath: String, callback: (Boolean) -> Unit){
        firebaseAccess.deleteRegister(collection = collection, documentPath = documentPath){success ->
            callback(success)
        }
    }
}
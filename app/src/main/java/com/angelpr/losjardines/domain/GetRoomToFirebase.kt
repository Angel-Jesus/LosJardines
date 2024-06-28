package com.angelpr.losjardines.domain

import com.angelpr.losjardines.core.FirebaseAccess
import com.angelpr.losjardines.data.model.RoomModel

class GetRoomToFirebase {
    private val firebaseAccess = FirebaseAccess

    operator fun invoke(callback: (List<RoomModel>) -> Unit) {
        firebaseAccess.getRoom {roomList ->
            callback(roomList)
        }
    }
}
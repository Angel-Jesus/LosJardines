package com.angelpr.losjardines.data

import com.angelpr.losjardines.data.model.RoomModel
import com.google.firebase.firestore.QuerySnapshot

class GetRoomList(private val result: QuerySnapshot) {

    private var roomList = emptyList<RoomModel>()

    fun getList(): List<RoomModel>{
        for (index in 0 until result.count()) {
            roomList += RoomModel(
                roomNumber = result.documents[index].id,
                roomState = result.documents[index].data?.get("state").toString().toBoolean(),
                roomPrice = result.documents[index].data?.get("price").toString().toInt()
            )
        }
        return roomList
    }
}
package com.angelpr.losjardines.data

import com.angelpr.losjardines.data.model.RoomModel
import com.angelpr.losjardines.data.model.types.HeadNameDB
import com.google.firebase.firestore.QuerySnapshot

class GetRoomList(private val result: QuerySnapshot) {

    private var roomList = emptyList<RoomModel>()

    fun getList(): List<RoomModel>{
        for (index in 0 until result.count()) {
            roomList += RoomModel(
                roomNumber = result.documents[index].id,
                roomState = result.documents[index].data?.get(HeadNameDB.ROOM_R).toString().toBoolean(),
                roomPrice = result.documents[index].data?.get(HeadNameDB.PRICE_R).toString().toInt()
            )
        }
        return roomList
    }
}
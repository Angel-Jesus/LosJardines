package com.angelpr.losjardines.data

import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.HeadNameDB
import com.google.firebase.firestore.QuerySnapshot

class GetReservationList(private val result: QuerySnapshot) {
    private var reservationList = emptyList<ReservationModel>()

    fun getList(): List<ReservationModel>{
        for (index in 0 until result.count()) {
            reservationList += ReservationModel(
                id = result.documents[index].id,
                room = result.documents[index].data?.get(HeadNameDB.ROOM_RV).toString().toInt(),
                dateReservation = result.documents[index].data?.get(HeadNameDB.DATE_RV).toString(),
                dateEnter = result.documents[index].data?.get(HeadNameDB.DATE_ENTER_RV).toString(),
                dateExit = result.documents[index].data?.get(HeadNameDB.DATE_EXIT_RV).toString(),
                numberNight = result.documents[index].data?.get(HeadNameDB.NUMBER_NIGHT_RV).toString().toInt(),
                numberPassenger = result.documents[index].data?.get(HeadNameDB.NUMBER_PSG_RV).toString().toInt(),
                typeService = result.documents[index].data?.get(HeadNameDB.SERVICE_RV).toString(),
                name = result.documents[index].data?.get(HeadNameDB.NAME_RV).toString(),
                dni = result.documents[index].data?.get(HeadNameDB.DNI_RV).toString(),
                nationality = result.documents[index].data?.get(HeadNameDB.NATIONALITY_RV).toString(),
                fee = result.documents[index].data?.get(HeadNameDB.FEE_RV).toString(),
                phoneEmail = result.documents[index].data?.get(HeadNameDB.PHONE_EMAIL_RV).toString(),
                observation = result.documents[index].data?.get(HeadNameDB.OBSERVATION_RV).toString()
            )
        }
        return reservationList
    }
}
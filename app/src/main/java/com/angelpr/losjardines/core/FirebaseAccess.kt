package com.angelpr.losjardines.core

import android.util.Log
import com.angelpr.losjardines.data.GetOfFilter
import com.angelpr.losjardines.data.GetRoomList
import com.angelpr.losjardines.data.model.types.ActionProcess
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.FilterType
import com.angelpr.losjardines.data.model.types.HeadNameDB
import com.angelpr.losjardines.data.model.RoomModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Calendar

class FirebaseAccess {

    private val db: FirebaseFirestore
        get() = Firebase.firestore

    fun sendRegister(data: ClientInfoModel, succesListener: (ActionProcess) -> Unit) {
        // Get date and time that is the documentPath of Cloud Firestore
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = convertIntToStr(Calendar.getInstance().get(Calendar.MONTH) + 1)
        val day = convertIntToStr(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        val hour = convertIntToStr(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        val minute = convertIntToStr(Calendar.getInstance().get(Calendar.MINUTE))
        val second = convertIntToStr(Calendar.getInstance().get(Calendar.SECOND))

        val documentPath = "$year$month$day$hour$minute$second"

        val client = hashMapOf(
            HeadNameDB.AYN_DB to data.name,
            HeadNameDB.DNI_DB to data.dni,
            HeadNameDB.DATE_DB to data.date,
            HeadNameDB.TIME_DB to data.hour,
            HeadNameDB.OBSERVATION_DB to data.observation,
            HeadNameDB.PRICE_DB to data.price,
            HeadNameDB.ORIGIN_DB to data.origin,
            HeadNameDB.NUMER_ROOM_DB to data.room
        )

        // Get last ID from Cloud Firestore
        val query = db.collection(data.collection)
        query
            .document(documentPath)
            .set(client)
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully written!")
                succesListener(ActionProcess.SUCCESS)
            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error adding document", error)
                succesListener(ActionProcess.ERROR)
            }

    }

    fun sendReservation(reservationModel: ReservationModel, succesListener: (ActionProcess) -> Unit){
        // Get date and time that is the documentPath of Cloud Firestore
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = convertIntToStr(Calendar.getInstance().get(Calendar.MONTH) + 1)
        val day = convertIntToStr(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        val hour = convertIntToStr(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        val minute = convertIntToStr(Calendar.getInstance().get(Calendar.MINUTE))
        val second = convertIntToStr(Calendar.getInstance().get(Calendar.SECOND))

        val documentPath = "$year$month$day$hour$minute$second"

        val clientReservation = hashMapOf(
            HeadNameDB.ROOM_RV_DB to reservationModel.room,
            HeadNameDB.DATE_RV to reservationModel.dateReservation,
            HeadNameDB.DATE_ENTER_RV to reservationModel.dateEnter,
            HeadNameDB.DATE_EXIT_RV to reservationModel.dateExit,
            HeadNameDB.NUMBER_NIGHT_RV to reservationModel.numberNight,
            HeadNameDB.NUMBER_PSG_RV to reservationModel.numberPassenger,
            HeadNameDB.SERVICE_RV to reservationModel.typeService,
            HeadNameDB.NAME_RV to reservationModel.name,
            HeadNameDB.DNI_RV to reservationModel.dni,
            HeadNameDB.NATIONALITY_RV to reservationModel.nationality,
            HeadNameDB.FEE_RV to reservationModel.fee,
            HeadNameDB.PHONE_EMAIL_RV to reservationModel.phoneEmail,
            HeadNameDB.OBSERVATION_RV to reservationModel.observation
        )
        Log.d("estado", "reser: ${documentPath}")
        // Get last ID from Cloud Firestore
        db.collection("Reservation")
            .document(documentPath)
            .set(clientReservation)
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully written!")
                succesListener(ActionProcess.SUCCESS)
            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error adding document", error)
                succesListener(ActionProcess.ERROR)
            }
    }

    fun getRegister(
        clientsRegisterModel: ClientsRegisterModel,
        callback: (ClientsRegisterModel, ActionProcess) -> Unit
    ) {

        val yearNow = Calendar.getInstance().get(Calendar.YEAR)
        val monthNow = Calendar.getInstance().get(Calendar.MONTH)

        val collection =
            if (clientsRegisterModel.timeFilter.ordinal > monthNow && clientsRegisterModel.filter != FilterType.Default) {
                (yearNow - 1).toString()
            } else {
                yearNow.toString()
            }

        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                when (clientsRegisterModel.filter) {
                    FilterType.Default -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegisterModel = clientsRegisterModel,
                            collection = collection
                        ).default(),
                        ActionProcess.SUCCESS
                    )

                    FilterType.Mont -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegisterModel = clientsRegisterModel,
                            collection = collection
                        ).month(),
                        ActionProcess.SUCCESS
                    )

                    FilterType.Dni -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegisterModel = clientsRegisterModel,
                            collection = collection
                        ).dni(),
                        ActionProcess.SUCCESS
                    )

                    FilterType.Origin -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegisterModel = clientsRegisterModel,
                            collection = collection
                        ).origin(),
                        ActionProcess.SUCCESS
                    )

                    FilterType.lastMonth -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegisterModel = clientsRegisterModel,
                            collection = collection
                        ).lastMonth(),
                        ActionProcess.SUCCESS
                    )
                }
            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error getting documents.", error)
                callback(ClientsRegisterModel(), ActionProcess.ERROR)
            }
    }

    fun getReservation(
        callback: (List<ReservationModel>, ActionProcess) -> Unit
    ){
        db.collection("Reservation")
            .get()
            .addOnSuccessListener { result ->

            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error getting documents.", error)
                callback(emptyList(), ActionProcess.ERROR)
            }
    }

    fun getRoom(callback: (List<RoomModel>, ActionProcess) -> Unit) {
        db.collection("Rooms")
            .get()
            .addOnSuccessListener { result ->
                callback(GetRoomList(result).getList(), ActionProcess.SUCCESS)
            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error getting documents.", error)
                callback(emptyList(), ActionProcess.ERROR)
            }
    }

    fun deleteRegister(collection: String, documentPath: String, callback: (ActionProcess) -> Unit) {
        db.collection(collection)
            .document(documentPath)
            .delete()
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully delete!")
                callback(ActionProcess.SUCCESS)
            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error adding document", error)
                callback(ActionProcess.ERROR)
            }
    }

    fun updateRegister(
        collection: String,
        documentPath: String,
        keyField: String,
        data: Any,
        callback: (ActionProcess) -> Unit
    ) {
        db.collection(collection)
            .document(documentPath)
            .update(keyField, data)
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully update!")
                callback(ActionProcess.SUCCESS)
            }
            .addOnFailureListener { error ->
                Log.w("estado", "Error adding document", error)
                callback(ActionProcess.ERROR)
            }
    }

    private fun convertIntToStr(value: Int): String = if (value < 10) {
        "0$value"
    } else {
        "$value"
    }

}
package com.angelpr.losjardines.data

import android.util.Log
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.HeadNameDB
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Calendar

object FirebaseAccess {

    private val db: FirebaseFirestore
        get() = Firebase.firestore

    fun sendRegister(clienInfo: Client, succesListener: (Boolean) -> Unit) {
        // Get date and time that is the documentPath of Cloud Firestore
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        val second = Calendar.getInstance().get(Calendar.SECOND)

        val documentPath = "$day$month$year$hour$minute$second"

        val client = hashMapOf(
            HeadNameDB.AYN_DB to clienInfo.name,
            HeadNameDB.DNI_DB to clienInfo.dni,
            HeadNameDB.DATE_DB to clienInfo.date,
            HeadNameDB.TIME_DB to clienInfo.hour,
            HeadNameDB.OBSERVATION_DB to clienInfo.observation,
            HeadNameDB.PRICE_DB to clienInfo.price,
            HeadNameDB.ORIGIN_DB to clienInfo.origin,
            HeadNameDB.NUMER_ROOM_DB to clienInfo.room
        )

        // Get last ID from Cloud Firestore
        val query = db.collection(year)
        query
            .document(documentPath)
            .set(client)
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully written!")
                succesListener(true)
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error adding document", error)
                succesListener(false)
            }

    }

    fun getRegister(clientsRegister: ClientsRegister, callback: (ClientsRegister) -> Unit) {

        val yearNow = Calendar.getInstance().get(Calendar.YEAR)
        val monthNow = Calendar.getInstance().get(Calendar.MONTH)

        val collection = if (clientsRegister.timeFilter.ordinal < monthNow) {
            (yearNow - 1).toString()
        } else {
            yearNow.toString()
        }

        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                when (clientsRegister.filter) {
                    FilterType.Default -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegister = clientsRegister
                        ).default()
                    )

                    FilterType.Mont -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegister = clientsRegister
                        ).month()
                    )

                    FilterType.Dni -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegister = clientsRegister
                        ).dni()
                    )

                    FilterType.Origin -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegister = clientsRegister
                        ).origin()
                    )

                    FilterType.lastMonth -> callback(
                        GetOfFilter(
                            result = result,
                            clientsRegister = clientsRegister
                        ).lastMonth()
                    )
                }
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error getting documents.", error)
            }
    }

    fun deleteRegister(collection: String, documentPath: String, callback: (Boolean) -> Unit) {
        db.collection(collection)
            .document(documentPath)
            .delete()
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully delete!")
                callback(true)
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error adding document", error)
                callback(false)
            }
    }

    fun updateRegister(collection: String, documentPath: String, key: String, data: Any, callback: (Boolean) -> Unit) {
        db.collection(collection)
            .document(documentPath)
            .update(key, data)
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully update!")
                callback(true)
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error adding document", error)
                callback(false)
            }
    }
}
package com.angelpr.losjardines.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.angelpr.losjardines.data.model.HeadNameDB
import com.angelpr.losjardines.data.model.InfoClient
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ClientsViewModel: ViewModel() {

    private val db = Firebase.firestore
    private var clientList = emptyList<InfoClient>()

    fun sendRegistertoFirebase() {
        val client = hashMapOf(
            HeadNameDB.AYN_DB to "Panduro Ruiz Fray Edgar",
            HeadNameDB.DNI_DB to "71605591",
            HeadNameDB.DATE_DB to "22/06/2024",
            HeadNameDB.TIME_DB to "13:45",
            HeadNameDB.OBSERVATION_DB to "",
            HeadNameDB.PRICE_DB to 35,
            HeadNameDB.ORIGIN_DB to "Lamas",
            HeadNameDB.NUMER_ROOM_DB to 103
        )

        // Get last ID from Cloud Firestore
        val query = db.collection("clientes")
        var lastIndex = 0L
        val countQuery = query.count()
        countQuery.get(AggregateSource.SERVER)
            .addOnSuccessListener { result ->
                lastIndex = result.count
                Log.d("estado", "Number of documents: $lastIndex")
                // Add a new document with a generated ID
                query
                    .document("AJ0")
                    .set(client)
                    .addOnSuccessListener {
                        Log.d("estado", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { error ->
                        Log.w("Firestore", "Error adding document", error)
                    }

            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting document count.", exception)
            }

    }

    fun getRegistertoFirebase() {
        db.collection("clientes")
            .get()
            .addOnSuccessListener { result ->

                for(i in result.count() downTo 1){
                    Log.d("estado", "${result.documents[i-1].id} => ${result.documents[i-1].data}")

                    clientList = clientList + InfoClient(
                        id = result.documents[i-1].id,
                        name = result.documents[i-1].data?.get(HeadNameDB.AYN_DB).toString(),
                        dni = result.documents[i-1].data?.get(HeadNameDB.DNI_DB).toString(),
                        date = result.documents[i-1].data?.get(HeadNameDB.DATE_DB).toString(),
                        time = result.documents[i-1].data?.get(HeadNameDB.TIME_DB).toString(),
                        observation = result.documents[i-1].data?.get(HeadNameDB.OBSERVATION_DB).toString(),
                        price = result.documents[i-1].data?.get(HeadNameDB.PRICE_DB).toString().toInt(),
                        origin = result.documents[i-1].data?.get(HeadNameDB.ORIGIN_DB).toString(),
                        room = result.documents[i-1].data?.get(HeadNameDB.NUMER_ROOM_DB).toString().toInt()
                    )

                    Log.d("estado", "Resultado: ClientList: $clientList")
                }
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error getting documents.", error)
            }
    }

    fun deleteRegistertoFirebase(){
        db.collection("clientes")
            .document("AJ0")
            .delete()
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully delete!")
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error adding document", error)
            }
    }

    fun updateRegistertoFirebase(){
        db.collection("clientes")
            .document("AJ0")
            .update(HeadNameDB.AYN_DB, "Panduro Ruiz Pancho")
            .addOnSuccessListener {
                Log.d("estado", "DocumentSnapshot successfully update!")
            }
            .addOnFailureListener { error ->
                Log.w("Firestore", "Error adding document", error)
            }
    }
}
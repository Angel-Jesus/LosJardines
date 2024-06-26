package com.angelpr.losjardines.data

import android.icu.util.Calendar
import android.util.Log
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.data.model.HeadNameDB
import com.angelpr.losjardines.data.model.Months
import com.google.firebase.firestore.QuerySnapshot

class GetOfFilter(private val result: QuerySnapshot, private val collection: String, private val clientsRegister: ClientsRegister) {

    private var clientList = emptyList<Client>()
    private val monthNow =
        Calendar.getInstance().get(Calendar.MONTH) + 1 // Function return 0 in January

    fun default(): ClientsRegister {
        for (index in result.count() downTo 1) {
            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)
            val monthInt = monthResultStr.toString().split("/")[1].toInt()

            if (monthNow == monthInt) {
                clientList +=
                    Client(
                        collection = collection,
                        id = result.documents[index - 1].id,
                        name = result.documents[index - 1].data?.get(HeadNameDB.AYN_DB).toString(),
                        dni = result.documents[index - 1].data?.get(HeadNameDB.DNI_DB).toString(),
                        date = result.documents[index - 1].data?.get(HeadNameDB.DATE_DB).toString(),
                        hour = result.documents[index - 1].data?.get(HeadNameDB.TIME_DB).toString(),
                        observation = result.documents[index - 1].data?.get(HeadNameDB.OBSERVATION_DB)
                            .toString(),
                        price = result.documents[index - 1].data?.get(HeadNameDB.PRICE_DB)
                            .toString().toInt(),
                        origin = result.documents[index - 1].data?.get(HeadNameDB.ORIGIN_DB)
                            .toString(),
                        room = result.documents[index - 1].data?.get(HeadNameDB.NUMER_ROOM_DB)
                            .toString().toInt()
                    )
            } else {
                break
            }
        }
        return ClientsRegister(clientsList = clientList, loading = false)
    }

    fun origin(): ClientsRegister {
        var flag = false

        val isMonthEmpty = clientsRegister.timeFilter == Months.NONE
        val monthFilter = clientsRegister.timeFilter.ordinal + 1

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)

            val monthInt = monthResultStr.toString().split("/")[1].toInt()
            val origin = result.documents[index - 1].get(HeadNameDB.ORIGIN_DB)

            if (isMonthEmpty.and(origin == clientsRegister.descriptionFilter) || isMonthEmpty.not().and(
                    origin == clientsRegister.descriptionFilter && monthInt == monthFilter
                )
            ) {
                clientList +=
                    Client(
                        collection = collection,
                        id = result.documents[index - 1].id,
                        name = result.documents[index - 1].data?.get(HeadNameDB.AYN_DB).toString(),
                        dni = result.documents[index - 1].data?.get(HeadNameDB.DNI_DB).toString(),
                        date = result.documents[index - 1].data?.get(HeadNameDB.DATE_DB).toString(),
                        hour = result.documents[index - 1].data?.get(HeadNameDB.TIME_DB).toString(),
                        observation = result.documents[index - 1].data?.get(HeadNameDB.OBSERVATION_DB)
                            .toString(),
                        price = result.documents[index - 1].data?.get(HeadNameDB.PRICE_DB)
                            .toString().toInt(),
                        origin = result.documents[index - 1].data?.get(HeadNameDB.ORIGIN_DB)
                            .toString(),
                        room = result.documents[index - 1].data?.get(HeadNameDB.NUMER_ROOM_DB)
                            .toString().toInt()
                    )
                flag = !isMonthEmpty
            } else {
                if (flag) {
                    break
                }
            }
        }
        return ClientsRegister(clientsList = clientList, loading = false)
    }

    fun month(): ClientsRegister {
        var flag = false
        val monthFilter = clientsRegister.timeFilter.ordinal + 1

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)
            val monthInt = monthResultStr.toString().split("/")[1].toInt()
            //Log.d("estado", "id: ${result.count()}")

            if (monthFilter == monthInt) {
                clientList +=
                    Client(
                        collection = collection,
                        id = result.documents[index - 1].id,
                        name = result.documents[index - 1].data?.get(HeadNameDB.AYN_DB).toString(),
                        dni = result.documents[index - 1].data?.get(HeadNameDB.DNI_DB).toString(),
                        date = result.documents[index - 1].data?.get(HeadNameDB.DATE_DB).toString(),
                        hour = result.documents[index - 1].data?.get(HeadNameDB.TIME_DB).toString(),
                        observation = result.documents[index - 1].data?.get(HeadNameDB.OBSERVATION_DB)
                            .toString(),
                        price = result.documents[index - 1].data?.get(HeadNameDB.PRICE_DB)
                            .toString().toInt(),
                        origin = result.documents[index - 1].data?.get(HeadNameDB.ORIGIN_DB)
                            .toString(),
                        room = result.documents[index - 1].data?.get(HeadNameDB.NUMER_ROOM_DB)
                            .toString().toInt()
                    )
                flag = true
            } else {
                if (flag) {
                    break
                }
            }
        }
        return ClientsRegister(clientsList = clientList, loading = false)
    }

    fun dni(): ClientsRegister {
        var flag = false
        val monthFilter = clientsRegister.timeFilter.ordinal + 1
        val isMonthEmpty = clientsRegister.timeFilter == Months.NONE

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)

            val monthInt = monthResultStr.toString().split("/")[1].toInt()
            val dni = result.documents[index - 1].get(HeadNameDB.DNI_DB)

            if (isMonthEmpty.and(dni == clientsRegister.descriptionFilter) || isMonthEmpty.not().and(
                    dni == clientsRegister.descriptionFilter && monthInt == monthFilter
                )
            ) {
                clientList +=
                    Client(
                        collection = collection,
                        id = result.documents[index - 1].id,
                        name = result.documents[index - 1].data?.get(HeadNameDB.AYN_DB).toString(),
                        dni = result.documents[index - 1].data?.get(HeadNameDB.DNI_DB).toString(),
                        date = result.documents[index - 1].data?.get(HeadNameDB.DATE_DB).toString(),
                        hour = result.documents[index - 1].data?.get(HeadNameDB.TIME_DB).toString(),
                        observation = result.documents[index - 1].data?.get(HeadNameDB.OBSERVATION_DB)
                            .toString(),
                        price = result.documents[index - 1].data?.get(HeadNameDB.PRICE_DB)
                            .toString().toInt(),
                        origin = result.documents[index - 1].data?.get(HeadNameDB.ORIGIN_DB)
                            .toString(),
                        room = result.documents[index - 1].data?.get(HeadNameDB.NUMER_ROOM_DB)
                            .toString().toInt()
                    )
                flag = !isMonthEmpty
            } else {
                if (flag) {
                    break
                }
            }
        }

        return ClientsRegister(clientsList = clientList, loading = false)
    }

    fun lastMonth(): ClientsRegister {
        var flag = false
        val monthFilter = clientsRegister.timeFilter.ordinal + 1

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)
            val monthInt = monthResultStr.toString().split("/")[1].toInt()

            if (monthFilter == monthInt) {
                clientList +=
                    Client(
                        collection = collection,
                        id = result.documents[index - 1].id,
                        name = result.documents[index - 1].data?.get(HeadNameDB.AYN_DB).toString(),
                        dni = result.documents[index - 1].data?.get(HeadNameDB.DNI_DB).toString(),
                        date = result.documents[index - 1].data?.get(HeadNameDB.DATE_DB).toString(),
                        hour = result.documents[index - 1].data?.get(HeadNameDB.TIME_DB).toString(),
                        observation = result.documents[index - 1].data?.get(HeadNameDB.OBSERVATION_DB)
                            .toString(),
                        price = result.documents[index - 1].data?.get(HeadNameDB.PRICE_DB)
                            .toString().toInt(),
                        origin = result.documents[index - 1].data?.get(HeadNameDB.ORIGIN_DB)
                            .toString(),
                        room = result.documents[index - 1].data?.get(HeadNameDB.NUMER_ROOM_DB)
                            .toString().toInt()
                    )
                flag = true
            } else {
                if (flag) {
                    break
                }
            }
        }
        return ClientsRegister(clientsList = clientList, loading = false)
    }

}
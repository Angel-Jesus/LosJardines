package com.angelpr.losjardines.data

import android.icu.util.Calendar
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.types.HeadNameDB
import com.angelpr.losjardines.data.model.types.Months
import com.google.firebase.firestore.QuerySnapshot

class GetOfFilter(private val result: QuerySnapshot, private val collection: String, private val clientsRegisterModel: ClientsRegisterModel) {

    private var clientInfoModelList = emptyList<ClientInfoModel>()
    private val monthNow =
        Calendar.getInstance().get(Calendar.MONTH) + 1 // Function return 0 in January

    fun default(): ClientsRegisterModel {
        for (index in result.count() downTo 1) {
            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)
            val monthInt = monthResultStr.toString().split("/")[1].toInt()

            if (monthNow == monthInt) {
                clientInfoModelList +=
                    ClientInfoModel(
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
        return ClientsRegisterModel(clientsList = clientInfoModelList)
    }

    fun origin(): ClientsRegisterModel {
        var flag = false

        val isMonthEmpty = clientsRegisterModel.timeFilter == Months.NONE
        val monthFilter = clientsRegisterModel.timeFilter.ordinal + 1

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)

            val monthInt = monthResultStr.toString().split("/")[1].toInt()
            val origin = result.documents[index - 1].get(HeadNameDB.ORIGIN_DB)

            if (isMonthEmpty.and(origin == clientsRegisterModel.descriptionFilter) || isMonthEmpty.not().and(
                    origin == clientsRegisterModel.descriptionFilter && monthInt == monthFilter
                )
            ) {
                clientInfoModelList +=
                    ClientInfoModel(
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
        return ClientsRegisterModel(clientsList = clientInfoModelList)
    }

    fun month(): ClientsRegisterModel {
        var flag = false
        val monthFilter = clientsRegisterModel.timeFilter.ordinal + 1

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)
            val monthInt = monthResultStr.toString().split("/")[1].toInt()
            //Log.d("estado", "id: ${result.count()}")

            if (monthFilter == monthInt) {
                clientInfoModelList +=
                    ClientInfoModel(
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
        return ClientsRegisterModel(clientsList = clientInfoModelList)
    }

    fun dni(): ClientsRegisterModel {
        var flag = false
        val monthFilter = clientsRegisterModel.timeFilter.ordinal + 1
        val isMonthEmpty = clientsRegisterModel.timeFilter == Months.NONE

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)

            val monthInt = monthResultStr.toString().split("/")[1].toInt()
            val dni = result.documents[index - 1].get(HeadNameDB.DNI_DB)

            if (isMonthEmpty.and(dni == clientsRegisterModel.descriptionFilter) || isMonthEmpty.not().and(
                    dni == clientsRegisterModel.descriptionFilter && monthInt == monthFilter
                )
            ) {
                clientInfoModelList +=
                    ClientInfoModel(
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

        return ClientsRegisterModel(clientsList = clientInfoModelList)
    }

    fun lastMonth(): ClientsRegisterModel {
        var flag = false
        val monthFilter = clientsRegisterModel.timeFilter.ordinal + 1

        for (index in result.count() downTo 1) {

            val monthResultStr = result.documents[index - 1].get(HeadNameDB.DATE_DB)
            val monthInt = monthResultStr.toString().split("/")[1].toInt()

            if (monthFilter == monthInt) {
                clientInfoModelList +=
                    ClientInfoModel(
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
        return ClientsRegisterModel(clientsList = clientInfoModelList)
    }

}
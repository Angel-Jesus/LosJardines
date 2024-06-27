package com.angelpr.losjardines.data

import android.icu.util.Calendar
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.Months
import com.angelpr.losjardines.data.model.StatiticsModel

class StatisticsProvider {

    private var statisticsDay = emptyList<StatiticsModel.Description>()
    private var statisticsRoom = emptyList<StatiticsModel.Description>()
    private var statisticsCity = emptyList<StatiticsModel.Description>()

    fun getStatistics(clientsRegister: List<ClientInfoModel>): StatiticsModel {


        // Get how many days there are in the previous month
        val calendar = Calendar.getInstance()
        val year = clientsRegister[0].collection.toInt()
        val month = if (calendar.get(Calendar.MONTH) == Months.DECEMBER.ordinal) {
            Months.JANUARY.ordinal
        } else {
            calendar.get(Calendar.MONTH) - 1
        }

        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
        }

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Get statistics about how many clients there are in each day
        val clientsEachDay = clientsRegister.groupingBy { it.date }.eachCount()
        for (day in 1..daysInMonth) {
            val dayStr = if (day < 10) {
                "0$day"
            } else {
                "$day"
            }
            val months = if (month < 10) {
                "0${month + 1}"
            } else {
                "${month + 1}"
            }
            statisticsDay += StatiticsModel.Description(
                description = "Dia $day",
                value = clientsEachDay["$dayStr$months$year"] ?: 0
            )
        }

        // Get statistics about how many clients there are in each room
        val clientsEachRoom = clientsRegister.groupingBy { it.room }.eachCount()
        clientsEachRoom.forEach { (room, count) ->
            statisticsRoom += StatiticsModel.Description(
                description = "Habitacion $room",
                value = count
            )
        }
        // Get statistics about how many clients there are in each city
        val clientsEachCity = clientsRegister.groupingBy { it.origin }.eachCount()
        clientsEachCity.forEach { (city, count) ->
            statisticsCity += StatiticsModel.Description(
                description = city,
                value = count
            )
        }

        return StatiticsModel(
            statisticsDays = statisticsDay,
            statisticsRoom = statisticsRoom,
            statisticsCity = statisticsCity
        )
    }
}
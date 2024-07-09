package com.angelpr.losjardines.data

import android.icu.util.Calendar
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.types.Months
import com.angelpr.losjardines.data.model.StatisticsModel
import com.github.mikephil.charting.data.BarEntry

class StatisticsProvider {

    private var labelDay = emptyList<String>()
    private var entrieDays = emptyList<BarEntry>()

    private var labelRoom = emptyList<String>()
    private var entrieRoom = emptyList<BarEntry>()

    private var labelCity = emptyList<String>()
    private var entrieCity = emptyList<BarEntry>()

    fun getStatistics(monthLast: String, clientsRegister: List<ClientInfoModel>): StatisticsModel {


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

            labelDay += "$day"
            entrieDays += BarEntry((day - 1).toFloat(), clientsEachDay["$dayStr/$months/$year"]?.toFloat() ?: 0.toFloat())

        }

        //Log.d("estado", "daysLabel: $labelDay")
        //Log.d("estado", "daysEntrie: $entrieDays")

        val statisticsDay = StatisticsModel.Description(
            maxValue = entrieDays.maxOf { it.y.toInt() },
            label = labelDay,
            entrie = entrieDays
        )


        // Get statistics about how many clients there are in each room
        val clientsEachRoom = clientsRegister.groupingBy { it.room }.eachCount()
        var index = 0
        clientsEachRoom.forEach { (room, count) ->
            labelRoom += "$room"
            entrieRoom += BarEntry(index.toFloat(), count.toFloat())
            index++
        }

        val statisticsRoom = StatisticsModel.Description(
            maxValue = entrieRoom.maxOf { it.y.toInt() },
            label = labelRoom,
            entrie = entrieRoom
        )


        // Get statistics about how many clients there are in each city
        val clientsEachCity = clientsRegister.groupingBy { it.origin }.eachCount()
        index = 0
        clientsEachCity.forEach { (city, count) ->
            labelCity += city
            entrieCity += BarEntry(index.toFloat(), count.toFloat())
            index++
        }

        val statisticsCity = StatisticsModel.Description(
            maxValue = entrieCity.maxOf { it.y.toInt() },
            label = labelCity,
            entrie = entrieCity
        )

        return StatisticsModel(month = monthLast, statisticsData = listOf(statisticsDay, statisticsRoom, statisticsCity))
    }
}
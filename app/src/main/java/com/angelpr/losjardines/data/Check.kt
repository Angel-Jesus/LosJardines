package com.angelpr.losjardines.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ReservationModel
import java.time.Duration
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class Check {

    fun reservationNotExistRg(
        dataRegister: ClientInfoModel,
        dataListReservation: List<ReservationModel>
    ): Boolean {
        // Check if there are reservations for the room in this day
        for (reservation in dataListReservation) {
            if (reservation.room == dataRegister.room) {
                val days = getDaysBetweenDays(dataRegister.date, reservation.dateExit)
                if ((days <= reservation.numberNight) && (days >= 0)) {
                    Log.d("estado", "existe")
                    return false
                }
            }
        }
        return true
    }

    fun reservationNotExistRs(
        dataReservation: ReservationModel,
        dataListReservation: List<ReservationModel>
    ): Boolean {
        // Check if there are reservations for the room in this day
        for (reservation in dataListReservation) {
            if (reservation.room == dataReservation.room) {
                val days = getDaysBetweenDays(dataReservation.dateEnter, reservation.dateExit)
                if ((days < reservation.numberNight) && (days >= 0)) {
                    Log.d("estado", "existe")
                    return false
                }
            }
        }
        return true
    }

    private fun getDaysBetweenDays(startDay: String, endDay: String): Long {

        val a = startDay.split("/")
        val b = endDay.split("/")

        val firstDay = LocalDate.of(a[2].toInt(), a[1].toInt(), a[0].toInt())
        val lastDay = LocalDate.of(b[2].toInt(), b[1].toInt(), b[0].toInt())

        return Duration.between(firstDay.atStartOfDay(), lastDay.atStartOfDay()).toDays()
    }
}
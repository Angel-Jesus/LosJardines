package com.angelpr.losjardines.ui.recycleView

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.databinding.ItemReservationBinding

class ReservationReViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemReservationBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(reservationMode: ReservationModel) {

        // Set text in each TextView
        binding.editRoom.text = reservationMode.room.toString()
        binding.editDateRv.text = reservationMode.dateReservation
        binding.editDateEnter.text = reservationMode.dateEnter
        binding.editDateExit.text = reservationMode.dateExit
        binding.editNumberNight.text = reservationMode.numberNight.toString()
        binding.editNumberPassenger.text = reservationMode.numberPassenger.toString()
        binding.editTypeService.text = reservationMode.typeService
        binding.editName.text = reservationMode.name
        binding.editDni.text = reservationMode.dni
        binding.editNationality.text = reservationMode.nationality
        binding.editFee.text = reservationMode.fee
        binding.editPhoneEmail.text = reservationMode.phoneEmail
        binding.editObservation.text = reservationMode.observation



    }



}
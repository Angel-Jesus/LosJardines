package com.angelpr.losjardines.ui.recycleView

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.TopicReservation
import com.angelpr.losjardines.databinding.ItemReservationRecycleBinding

class ReservationReViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemReservationRecycleBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(
        reservationMode: ReservationModel,
        reservation: List<ReservationModel>,
        onClick: () -> Unit
    ) {
        when (reservationMode.description) {
            TopicReservation.LOGO -> {
                logoShow()
            }

            TopicReservation.ROOM -> {
                textInputShow()
                binding.txtDescription.text = "N° Habitacion"
            }

            TopicReservation.RESERVATION_DATE -> {
                textInputShow()
                binding.txtDescription.text = "Fecha reservacion"
            }

            TopicReservation.RESERVATION_DATE_ENTER -> {
                textInputShow()
                binding.txtDescription.text = "Fecha de Ingreso"
            }

            TopicReservation.RESERVATION_DATE_EXIT -> {
                textInputShow()
                binding.txtDescription.text = "Fecha de Salida"
            }

            TopicReservation.NUMBER_NIGHT -> {
                textInputShow()
                binding.txtDescription.text = "N° Noches"
            }

            TopicReservation.NUMBER_PASSENGER -> {
                textInputShow()
                binding.txtDescription.text = "N° Pasajeros"
            }

            TopicReservation.TYPE_SERVICE -> {
                textInputShow()
                binding.txtDescription.text = "Tipo de Habitacion/Servicio"
            }

            TopicReservation.NAME -> {
                textInputShow()
                binding.txtDescription.text = "Nombres y Apellidos Pasajero"
            }

            TopicReservation.DNI -> {
                textInputShow()
                binding.txtDescription.text = "DNI"
            }

            TopicReservation.NATIONALITY -> {
                textInputShow()
                binding.txtDescription.text = "Nacionalidad"
            }

            TopicReservation.FEE -> {
                textInputShow()
                binding.txtDescription.text = "Tarifa"
            }

            TopicReservation.PHONE_EMAIL -> {
                textInputShow()
                binding.txtDescription.text = "Numero contacto/ Correo electronico"
            }

            TopicReservation.OBSERVATION -> {
                textInputShow()
                binding.txtDescription.text = "Observaciones"
            }

            TopicReservation.BUTTOM_SAVE -> {
                buttomShow()
            }
        }


        binding.inputTxtReservation.addTextChangedListener { text ->
            reservation[reservationMode.description.ordinal].valueText = text.toString()
        }

        binding.btnSaveReservation.setOnClickListener {
            onClick()
        }
    }

    private fun textInputShow() {
        binding.imageViewLogo.isGone = true
        binding.inputTxtReservation.isGone = false
        binding.txtDescription.isGone = false
        binding.btnSaveReservation.isGone = true
    }

    private fun logoShow() {
        binding.imageViewLogo.isGone = false
        binding.inputTxtReservation.isGone = true
        binding.txtDescription.isGone = true
        binding.btnSaveReservation.isGone = true
    }

    private fun buttomShow() {
        binding.imageViewLogo.isGone = true
        binding.inputTxtReservation.isGone = true
        binding.txtDescription.isGone = true
        binding.btnSaveReservation.isGone = false
    }
}
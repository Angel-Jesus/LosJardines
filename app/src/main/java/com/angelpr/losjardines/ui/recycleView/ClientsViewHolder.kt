package com.angelpr.losjardines.ui.recycleView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.HeadNameDB
import com.angelpr.losjardines.data.model.UpdateData
import com.angelpr.losjardines.databinding.ItemClientsBinding

class ClientsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemClientsBinding.bind(view)

    fun render(clien: Client, onClickListener: (UpdateData) -> Unit) {
        binding.editRoom.text = clien.room.toString()
        binding.editHour.text = clien.hour
        binding.editDate.text = clien.date
        binding.editName.text = clien.name
        binding.editDni.text = clien.dni
        binding.editOrigin.text = clien.origin
        binding.editPrice.text = clien.price.toString()
        binding.editObservation.text = clien.observation

        binding.editRoom.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.NUMER_ROOM_DB,
                    data = clien.room,
                )
            )
        }
        binding.editHour.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.TIME_DB,
                    data = clien.hour,
                )
            )
        }
        binding.editDate.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.DATE_DB,
                    data = clien.date,
                )
            )
        }
        binding.editName.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.AYN_DB,
                    data = clien.name,
                )
            )
        }
        binding.editDni.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.DNI_DB,
                    data = clien.dni,
                )
            )
        }
        binding.editOrigin.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.ORIGIN_DB,
                    data = clien.origin,
                )
            )
        }
        binding.editPrice.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.PRICE_DB,
                    data = clien.price,
                )
            )
        }
        binding.editObservation.setOnClickListener {
            onClickListener(
                UpdateData(
                    collection = clien.collection,
                    documentPath = clien.id,
                    keyField = HeadNameDB.OBSERVATION_DB,
                    data = clien.observation,
                )
            )
        }

    }
}
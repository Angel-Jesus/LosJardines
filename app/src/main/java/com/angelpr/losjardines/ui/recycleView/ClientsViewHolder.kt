package com.angelpr.losjardines.ui.recycleView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.HeadNameDB
import com.angelpr.losjardines.data.model.UpdateDataModel
import com.angelpr.losjardines.databinding.ItemClientsBinding

class ClientsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemClientsBinding.bind(view)

    fun render(client: ClientInfoModel,position: Int ,onClickListener: (UpdateDataModel, Int) -> Unit) {
        binding.editRoom.text = client.room.toString()
        binding.editHour.text = client.hour
        binding.editDate.text = client.date
        binding.editName.text = client.name
        binding.editDni.text = client.dni
        binding.editOrigin.text = client.origin
        binding.editPrice.text = client.price.toString()
        binding.editObservation.text = client.observation

        binding.editRoom.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.NUMER_ROOM_DB,
                    data = client.room,
                ),
                position
            )
        }
        binding.editHour.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.TIME_DB,
                    data = client.hour,
                ),
                position
            )
        }
        binding.editDate.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.DATE_DB,
                    data = client.date,
                ),
                position
            )
        }
        binding.editName.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.AYN_DB,
                    data = client.name,
                ),
                position
            )
        }
        binding.editDni.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.DNI_DB,
                    data = client.dni,
                ),
                position
            )
        }
        binding.editOrigin.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.ORIGIN_DB,
                    data = client.origin,
                ),
                position
            )
        }
        binding.editPrice.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.PRICE_DB,
                    data = client.price,
                ),
                position
            )
        }
        binding.editObservation.setOnClickListener {
            onClickListener(
                UpdateDataModel(
                    collection = client.collection,
                    documentPath = client.id,
                    keyField = HeadNameDB.OBSERVATION_DB,
                    data = client.observation,
                ),
                position
            )
        }

    }
}
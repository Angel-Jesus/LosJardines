package com.angelpr.losjardines.ui.recycleView

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.RoomModel
import com.angelpr.losjardines.databinding.ItemRoomsBinding

class RoomsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemRoomsBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(context: Context, room: RoomModel, onClickListener: (RoomModel) -> Unit){
        binding.txtBed.text = "Habitacion: ${room.roomNumber}"
        binding.txtCost.text = "Precio: ${room.roomPrice}"
        binding.txtState.text = if(room.roomState){ "DISPONIBLE" } else { "OCUPADO" }

        if(room.roomState)
        {
            binding.btnState.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.room_green)
        }
        else
        {
            binding.btnState.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.room_red)
        }

        binding.layoutBed.setOnClickListener {
            onClickListener(room)
        }
    }
}
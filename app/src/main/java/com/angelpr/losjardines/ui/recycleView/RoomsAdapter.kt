package com.angelpr.losjardines.ui.recycleView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.RoomModel

class RoomsAdapter(
    private val context: Context,
    private val rooms: List<RoomModel>,
    private val onClickListener: (RoomModel) -> Unit
) : RecyclerView.Adapter<RoomsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoomsViewHolder(layoutInflater.inflate(R.layout.item_rooms, parent, false))
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        val room = rooms[position]
        holder.render(context, room, onClickListener)
    }
}
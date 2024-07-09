package com.angelpr.losjardines.ui.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ReservationModel

class ReservationReAdapter(private val reservationList: List<ReservationModel>, private val onClick: () -> Unit): RecyclerView.Adapter<ReservationReViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationReViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReservationReViewHolder(layoutInflater.inflate(R.layout.item_reservation_recycle, parent, false))
    }

    override fun getItemCount(): Int = reservationList.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: ReservationReViewHolder, position: Int) {
        val item = reservationList[position]
        holder.render(item, reservationList, onClick)
    }

    fun getAllValue():List<ReservationModel>{
        return reservationList.filterIndexed { index, _-> index != 0 }
    }
}
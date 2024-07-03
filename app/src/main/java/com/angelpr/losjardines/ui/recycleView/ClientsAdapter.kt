package com.angelpr.losjardines.ui.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.UpdateDataModel

class ClientsAdapter(private val clientsList: List<ClientInfoModel>, private val onclickListener: (UpdateDataModel, Int) -> Unit): RecyclerView.Adapter<ClientsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClientsViewHolder(layoutInflater.inflate(R.layout.item_clients, parent, false))
    }

    override fun getItemCount(): Int = clientsList.size

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val item = clientsList[position]
        holder.render(item, position ,onclickListener)
    }
}
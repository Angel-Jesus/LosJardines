package com.angelpr.losjardines.ui.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.UpdateData
import com.angelpr.losjardines.ui.viewmodel.ClientsViewModel

class ClientsAdapter(private val clientsList: List<Client>, private val onclickListener: (UpdateData) -> Unit): RecyclerView.Adapter<ClientsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClientsViewHolder(layoutInflater.inflate(R.layout.item_clients, parent, false))
    }

    override fun getItemCount(): Int = clientsList.size

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val item = clientsList[position]
        holder.render(item, onclickListener)
    }
}
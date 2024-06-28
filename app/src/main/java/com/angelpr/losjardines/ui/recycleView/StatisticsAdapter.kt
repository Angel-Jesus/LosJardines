package com.angelpr.losjardines.ui.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.StatisticsModel

class StatisticsAdapter(private val statisticsModel: StatisticsModel): RecyclerView.Adapter<StatisticsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StatisticsViewHolder(layoutInflater.inflate(R.layout.item_barchar, parent, false))
    }

    override fun getItemCount(): Int = statisticsModel.statisticsData.size

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val dataList = statisticsModel.statisticsData[position]
        holder.render(statisticsData = dataList, month = statisticsModel.month,position = position)
    }
}
package com.angelpr.losjardines.ui.recycleView

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.databinding.ItemReservationRecycleBinding
import com.angelpr.losjardines.ui.picker.GetPicker
import com.google.android.gms.common.api.internal.LifecycleActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationReViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemReservationRecycleBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(
        reservationMode: ReservationModel,
    ) {

    }



}
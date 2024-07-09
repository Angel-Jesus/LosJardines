package com.angelpr.losjardines.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.types.BodyReservation
import com.angelpr.losjardines.databinding.ActivityReservationBinding
import com.angelpr.losjardines.ui.recycleView.ReservationReAdapter

class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationBinding
    private lateinit var adapter: ReservationReAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        systemBar()

        binding.rvReservation.setHasFixedSize(true)
        binding.rvReservation.layoutManager = LinearLayoutManager(this)
        adapter = ReservationReAdapter(BodyReservation.reservationRegisterBody){
            onClickButtom()
        }
        binding.rvReservation.adapter = adapter

    }

    private fun onClickButtom() {
        Log.d("estado", "click: ${adapter.getAllValue()}")
    }

    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
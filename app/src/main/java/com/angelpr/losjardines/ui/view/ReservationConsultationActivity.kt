package com.angelpr.losjardines.ui.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.data.model.types.ActionProcess
import com.angelpr.losjardines.databinding.ActivityReservationConsultationBinding
import com.angelpr.losjardines.ui.dialogFragment.DialogFragmentResponseRs
import com.angelpr.losjardines.ui.recycleView.ReservationReAdapter
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ReservationConsultationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationConsultationBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReservationConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        systemBar()

        firebaseViewModel.getReservationData()
        DialogFragmentResponseRs(
            context = this,
            action = FirebaseViewModel.Action.GET,
            firebaseViewModel = firebaseViewModel
        ).show(supportFragmentManager, "DialogFragmentResponseRs")

        // Events of StateFlow
        lifecycleScope.launch {
            firebaseViewModel.stateReservationData.collect { uiStateReservation ->
                when (uiStateReservation.response) {
                    ActionProcess.LOADING -> {
                        Log.d("estado", "loading")
                        binding.recycleViewTable.isGone = true
                    }
                    ActionProcess.SUCCESS -> {
                        Log.d("estado", "success")
                        binding.recycleViewTable.isGone = false
                        recycleViewCreate(uiStateReservation.reservationList)
                    }

                    ActionProcess.ERROR -> {
                        Log.d("estado", "error")
                        binding.recycleViewTable.isGone = true
                    }

                    else -> Unit
                }
            }
        }

        // Event ToolBar action
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }
    }

    private fun recycleViewCreate(reservationList: List<ReservationModel>) {
        binding.recycleViewTable.setHasFixedSize(true)
        binding.recycleViewTable.layoutManager = LinearLayoutManager(this)
        binding.recycleViewTable.adapter = ReservationReAdapter(reservationList)
    }

    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
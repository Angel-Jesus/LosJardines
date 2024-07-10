package com.angelpr.losjardines.ui.view

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ReservationModel
import com.angelpr.losjardines.databinding.ActivityReservationBinding
import com.angelpr.losjardines.ui.picker.GetPicker
import com.angelpr.losjardines.ui.recycleView.ReservationReAdapter
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import kotlinx.coroutines.launch

class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()
    private val picker = GetPicker()
    private var isEnter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        systemBar()

        binding.editDateEnterRv.setOnClickListener {
            picker.date(this)
            isEnter = true
        }

        binding.editDateExitRv.setOnClickListener {
            picker.date(this)
            isEnter = false
        }

        lifecycleScope.launch {
            picker.pickerData.collect { uiDataPicker ->
                if(isEnter){
                    binding.editDateEnterRv.setText(uiDataPicker.dateValue)
                }
                else{
                    binding.editDateExitRv.setText(uiDataPicker.dateValue)
                }
            }
        }

        // Event to save reservation
        binding.btnSaveRv.setOnClickListener {
            sendReservation()
        }

        // Event to back previous activity
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }
    }

    private fun sendReservation(){
        val reservation = ReservationModel(
            room = binding.editRoomRv.text.toString().toInt(),
            dateReservation = getDateNow(),
            dateEnter = binding.editDateEnterRv.text.toString(),
            dateExit = binding.editDateExitRv.text.toString(),
            numberNight = binding.editNumberNightRv.text.toString().toInt(),
            numberPassenger = binding.editNumberPassengerRv.text.toString().toInt(),
            typeService = binding.editTypeServiceRv.text.toString(),
            name = binding.editNameRv.text.toString(),
            dni = binding.editDniRv.text.toString(),
            nationality = binding.editOriginRv.text.toString(),
            fee = binding.editFeeRv.text.toString(),
            phoneEmail = binding.editPhoneEmailRv.text.toString(),
            observation = binding.editObservationRv.text.toString()
        )
        //Log.d("estado", "Reservation: $reservation")
        firebaseViewModel.sendReservationData(reservation)
    }

    private fun getDateNow(): String{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val monthStr = if(month > 10){ month.toString() } else { "0$month" }
        val dayStr = if(day > 10){ day.toString() } else { "0$day" }

        return "$dayStr/$monthStr/$year"
    }

    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
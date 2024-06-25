package com.angelpr.losjardines.ui.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.data.model.SpinnerItem
import com.angelpr.losjardines.data.model.UpdateData
import com.angelpr.losjardines.databinding.ActivityConsultationBinding
import com.angelpr.losjardines.ui.recycleView.ClientsAdapter
import com.angelpr.losjardines.ui.viewmodel.ClientsViewModel

class ConsultationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultationBinding
    private val clientsViewModel: ClientsViewModel by viewModels()
    private val clientInit = ClientsRegister()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        systemBar()

        spinnerFilterOption()
        spinnerFilterMonth()

        // Event to get Data of cloud firebase
        clientsViewModel.getData(clientInit)

        // Events of liveData
        clientsViewModel.clientRegisterData.observe(this) { clientRegister ->
            if (clientRegister.loading) {

            } else {
                recycleViewCreate(clientRegister)
            }
        }

        // Event of recycleView


        // Events of setOnClickListener


        // Event to back previous activity
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }

    }

    private fun recycleViewCreate(clients: ClientsRegister) {
        binding.recycleViewTable.setHasFixedSize(true)
        binding.recycleViewTable.layoutManager = LinearLayoutManager(this)
        binding.recycleViewTable.adapter = ClientsAdapter(clients.clientsList){ data ->
            onItemSelected(data)
        }

    }

    private fun onItemSelected(data: UpdateData) {
        Log.d("estado", "data: $data")
    }

    private fun spinnerFilterMonth() {
        val adapterFilterMonth =
            ArrayAdapter(this, R.layout.spinner_item, SpinnerItem.SPINNER_MONTH)
        binding.spinnerMonths.adapter = adapterFilterMonth

    }

    private fun spinnerFilterOption() {
        val adapterFilterOption =
            ArrayAdapter(this, R.layout.spinner_item, SpinnerItem.SPINNER_FILTER)
        binding.spinnerFilter.adapter = adapterFilterOption

        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

        }

    }

    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
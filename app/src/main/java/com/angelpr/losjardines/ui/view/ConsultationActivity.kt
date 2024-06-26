package com.angelpr.losjardines.ui.view

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.Months
import com.angelpr.losjardines.data.model.SpinnerItem
import com.angelpr.losjardines.data.model.UpdateData
import com.angelpr.losjardines.databinding.ActivityConsultationBinding
import com.angelpr.losjardines.ui.recycleView.ClientsAdapter
import com.angelpr.losjardines.ui.viewmodel.ClientsViewModel

class ConsultationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultationBinding
    private val clientsViewModel: ClientsViewModel by viewModels()

    private var typeFilter: FilterType = FilterType.Default
    private var monthFilter: Months = Months.NONE

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
        getDefaultData()

        // Events of setOnClickListener
        binding.btnSearch.setOnClickListener {
            searchDataOfFilter()
        }

        binding.btnClear.setOnClickListener {
            // Clear data
            binding.spinnerFilter.setSelection(SpinnerItem.SpinnerPosition.None.ordinal)
            getDefaultData()
        }

        // Event to back previous activity
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }

    }

    private fun searchDataOfFilter() {
        val description = binding.descriptionFilter.text.toString()

        when {
            typeFilter != FilterType.Mont && description.isEmpty() -> {
                messageAlert("No puede dejar el campo vacio")
            }

            typeFilter == FilterType.Mont && monthFilter == Months.NONE -> {
                messageAlert("Seleccione un mes")
            }

            else -> {
                clientsViewModel.getData(
                    ClientsRegister(
                        filter = typeFilter,
                        descriptionFilter = description,
                        timeFilter = monthFilter
                    )
                )
                dialogViewLoadingGetData()
            }
        }
    }

    private fun getDefaultData() {
        clientsViewModel.getData(ClientsRegister())
        dialogViewLoadingGetData()
    }

    private fun dialogViewLoadingDU(){

    }

    private fun dialogViewLoadingGetData() {
        // Show Dialog Loading
        val dialog = Dialog(this).apply {
            setContentView(R.layout.dialog_box_loading)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        clientsViewModel.clientRegisterData.observe(this) { clientRegister ->
            if (clientRegister.loading) {
                binding.recycleViewTable.isGone = true

            } else {
                dialog.dismiss()
                binding.recycleViewTable.isGone = false
                recycleViewCreate(clientRegister)
            }
        }

        dialog.show()
    }

    private fun messageAlert(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Mensaje de Alerta")
            setMessage(message)
            setCancelable(true)
            setPositiveButton("OK") { btn, _ -> btn.cancel() }
            show()
        }
    }

    private fun recycleViewCreate(clients: ClientsRegister) {
        binding.recycleViewTable.setHasFixedSize(true)
        binding.recycleViewTable.layoutManager = LinearLayoutManager(this)
        binding.recycleViewTable.adapter = ClientsAdapter(clients.clientsList) { data ->
            onItemSelected(data)
        }

    }

    private fun onItemSelected(data: UpdateData) {
        // Select to update data and showing dialog
        Log.d("estado", "data: $data")
    }

    private fun spinnerFilterMonth() {
        val adapterFilterMonth =
            ArrayAdapter(this, R.layout.spinner_item, SpinnerItem.SPINNER_MONTH)
        binding.spinnerMonths.adapter = adapterFilterMonth

        binding.spinnerMonths.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> monthFilter = Months.NONE
                    1 -> monthFilter = Months.JANUARY
                    2 -> monthFilter = Months.FEBRUARY
                    3 -> monthFilter = Months.MARCH
                    4 -> monthFilter = Months.APRIL
                    5 -> monthFilter = Months.MAY
                    6 -> monthFilter = Months.JUNE
                    7 -> monthFilter = Months.JULY
                    8 -> monthFilter = Months.AUGUST
                    9 -> monthFilter = Months.SEPTEMBER
                    10 -> monthFilter = Months.OCTOBER
                    11 -> monthFilter = Months.NOVEMBER
                    12 -> monthFilter = Months.DECEMBER
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

        }
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
                when (position) {
                    SpinnerItem.SpinnerPosition.None.ordinal -> {
                        binding.layoutFilterFiller.isGone = true
                        binding.layoutBtnOption.isGone = true
                        typeFilter = FilterType.Default
                    }

                    SpinnerItem.SpinnerPosition.Origin.ordinal -> {
                        binding.layoutFilterFiller.isGone = false
                        binding.textInputLayout.isGone = false
                        binding.layoutBtnOption.isGone = false
                        typeFilter = FilterType.Origin
                    }

                    SpinnerItem.SpinnerPosition.Month.ordinal -> {
                        binding.layoutFilterFiller.isGone = false
                        binding.textInputLayout.isGone = true
                        binding.layoutBtnOption.isGone = false
                        typeFilter = FilterType.Mont
                    }

                    SpinnerItem.SpinnerPosition.DNI.ordinal -> {
                        binding.layoutFilterFiller.isGone = false
                        binding.textInputLayout.isGone = false
                        binding.layoutBtnOption.isGone = false
                        typeFilter = FilterType.Dni
                    }
                }


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
package com.angelpr.losjardines.ui.view

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.types.ActionProcess
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.types.FilterType
import com.angelpr.losjardines.data.model.types.Months
import com.angelpr.losjardines.data.model.types.SpinnerItem
import com.angelpr.losjardines.data.model.UpdateDataModel
import com.angelpr.losjardines.databinding.ActivityConsultationBinding
import com.angelpr.losjardines.ui.dialogFragment.DialogFragmentResponse
import com.angelpr.losjardines.ui.dialogFragment.DialogFragmentUpdate
import com.angelpr.losjardines.ui.recycleView.ClientsAdapter
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import kotlinx.coroutines.launch

class ConsultationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultationBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    private var typeFilter: FilterType = FilterType.Default
    private var monthFilter: Months = Months.NONE

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        systemBar()

        spinnerFilterOption()
        spinnerFilterMonth()

        // Event to get Data Default of cloud firebase
        firebaseViewModel.getData(ClientsRegisterModel())
        DialogFragmentResponse(
            context = this,
            actionRegister = FirebaseViewModel.ActionRegister.GET,
            firebaseViewModel = firebaseViewModel
        ).show(supportFragmentManager, "DialogFragmentResponse")

        // Events of StateFlow
        lifecycleScope.launch {
            firebaseViewModel.stateRegisterData.collect { uiStateRegister ->
                when (uiStateRegister.response) {
                    ActionProcess.LOADING -> {
                        Log.d("estado", "loading")
                        binding.recycleViewTable.isGone = true
                    }
                    ActionProcess.SUCCESS -> {
                        Log.d("estado", "success")
                        binding.recycleViewTable.isGone = false
                        recycleViewCreate(uiStateRegister.clientRegisterModel)
                    }

                    ActionProcess.ERROR -> {
                        Log.d("estado", "error")
                        binding.recycleViewTable.isGone = true
                    }
                }

                if(uiStateRegister.changeValue){
                    Log.d("estado", "changeValue")
                    firebaseViewModel.getData(ClientsRegisterModel())
                    DialogFragmentResponse(
                        context = this@ConsultationActivity,
                        actionRegister = FirebaseViewModel.ActionRegister.GET,
                        firebaseViewModel = firebaseViewModel
                    ).show(supportFragmentManager, "DialogFragmentResponse")
                }
            }

        }

        // Events of setOnClickListener
        binding.btnSearch.setOnClickListener {
            searchDataOfFilter()
        }

        binding.btnClear.setOnClickListener {
            // Clear data
            binding.spinnerFilter.setSelection(SpinnerItem.SpinnerPosition.None.ordinal)
            // Event to get Data Default of cloud firebase
            firebaseViewModel.getData(ClientsRegisterModel())
            DialogFragmentResponse(
                context = this,
                actionRegister = FirebaseViewModel.ActionRegister.GET,
                firebaseViewModel = firebaseViewModel
            ).show(supportFragmentManager, "DialogFragmentResponse")
        }

        // Event ToolBar action
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dataStatic -> {
                    Log.d("estado", "click dataStatic")
                    startActivity(Intent(this, StatisticsActivity::class.java))
                    true
                }

                R.id.loading -> {
                    Log.d("estado", "click loading")
                    firebaseViewModel.getData(ClientsRegisterModel())
                    DialogFragmentResponse(
                        context = this,
                        actionRegister = FirebaseViewModel.ActionRegister.GET,
                        firebaseViewModel = firebaseViewModel
                    ).show(supportFragmentManager, "DialogFragmentResponse")
                    true
                }

                R.id.rooms ->{
                    Log.d("estado", "click rooms")
                    startActivity(Intent(this, RoomActivity::class.java))
                    true
                }

                R.id.reservation -> {
                    Log.d("estado", "click reservation")
                    startActivity(Intent(this, ReservationActivity::class.java))
                    true
                }

                else -> false
            }
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
                firebaseViewModel.getData(
                    ClientsRegisterModel(
                        filter = typeFilter,
                        descriptionFilter = description,
                        timeFilter = monthFilter
                    )
                )
                DialogFragmentResponse(
                    context = this,
                    actionRegister = FirebaseViewModel.ActionRegister.GET,
                    firebaseViewModel = firebaseViewModel
                ).show(supportFragmentManager, "DialogFragmentResponse")
            }
        }
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

    private fun recycleViewCreate(clients: ClientsRegisterModel) {
        binding.recycleViewTable.setHasFixedSize(true)
        binding.recycleViewTable.layoutManager = LinearLayoutManager(this)
        binding.recycleViewTable.adapter = ClientsAdapter(clients.clientsList) { data, _ ->
            onItemSelected(data)
        }
    }

    private fun onItemSelected(data: UpdateDataModel) {
        // Select to update data and showing dialog
        DialogFragmentUpdate(
            activity = this,
            clienteViewModel = firebaseViewModel,
            dataUpdateModel = data
        ).show(supportFragmentManager, "DialogFragmentDU")
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}
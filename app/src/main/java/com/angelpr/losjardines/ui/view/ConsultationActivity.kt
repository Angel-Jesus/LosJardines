package com.angelpr.losjardines.ui.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ActionProcess
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.Months
import com.angelpr.losjardines.data.model.SpinnerItem
import com.angelpr.losjardines.data.model.UpdateDataModel
import com.angelpr.losjardines.databinding.ActivityConsultationBinding
import com.angelpr.losjardines.ui.dialogFragment.DialogFragmentUpdate
import com.angelpr.losjardines.ui.recycleView.ClientsAdapter
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel

class ConsultationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultationBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    private var typeFilter: FilterType = FilterType.Default
    private var monthFilter: Months = Months.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        systemBar()

        spinnerFilterOption()
        spinnerFilterMonth()

        // Event to get Data Default of cloud firebase
        dialogViewLoadingGetData()
        firebaseViewModel.getData(ClientsRegisterModel())

        // Events of LiveData
        firebaseViewModel.clientRegisterData.observe(this) { clientRegister ->
            if (clientRegister.loading) {
                binding.recycleViewTable.isGone = true

            } else {
                //Log.d("estado", "data: ${clientRegister.clientsList}")
                binding.recycleViewTable.isGone = false
                recycleViewCreate(clientRegister)
            }
        }

        firebaseViewModel.isDelete.observe(this) { isDelete ->
            when {
                isDelete!! == ActionProcess.LOADING -> {
                    dialogViewLoadingGetData()
                    Log.d("estado", "Delete loading")
                }
                isDelete == ActionProcess.SUCCESS -> {
                    firebaseViewModel.getData(ClientsRegisterModel())
                    Log.d("estado", "Delete sucess")
                }
                isDelete == ActionProcess.ERROR -> {
                    dialogViewError()
                    Log.d("estado", "Delete error")
                }
            }
        }

        firebaseViewModel.isUpdate.observe(this) { isUpdate ->
            when{
                isUpdate!! == ActionProcess.LOADING -> {
                    dialogViewLoadingGetData()
                    Log.d("estado", "Update loading")
                }
                isUpdate == ActionProcess.SUCCESS -> {
                    firebaseViewModel.getData(ClientsRegisterModel())
                    Log.d("estado", "Update sucess")
                }
                isUpdate == ActionProcess.ERROR -> {
                    dialogViewError()
                    Log.d("estado", "Update error")
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
            dialogViewLoadingGetData()
            firebaseViewModel.getData(ClientsRegisterModel())
        }

        // Event ToolBar action
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }

        binding.toolbar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.dataStatic -> {
                    Log.d("estado", "click dataStatic")
                    startActivity(Intent(this, StatisticsActivity::class.java))
                    true
                }
                R.id.loading -> {
                    Log.d("estado", "click loading")
                    dialogViewLoadingGetData()
                    firebaseViewModel.getData(ClientsRegisterModel())
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
                dialogViewLoadingGetData()
            }
        }
    }

    private fun dialogViewError() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_box_status)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val textInfo = dialog.findViewById<TextView>(R.id.textInfo)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        val imageView = dialog.findViewById<ImageView>(R.id.stateIcon)

        progressBar.isGone = true
        imageView.isGone = false

        textInfo.text = getString(R.string.txt_state_error)
        imageView.setImageResource(R.drawable.error)

        dialog.show()
    }

    private fun dialogViewLoadingGetData() {
        // Show Dialog Loading
        val dialog = Dialog(this).apply {
            setContentView(R.layout.dialog_box_loading)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog.show()

        firebaseViewModel.clientRegisterData.observe(this) { clientRegister ->
            if (clientRegister.loading.not()) {
                dialog.dismiss()
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
        binding.recycleViewTable.adapter = ClientsAdapter(clients.clientsList) { data ->
            onItemSelected(data)
        }
    }

    private fun onItemSelected(data: UpdateDataModel) {
        // Select to update data and showing dialog
        DialogFragmentUpdate(
            activity = this,
            clienteViewModel = firebaseViewModel,
            dataUpdate = data
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}
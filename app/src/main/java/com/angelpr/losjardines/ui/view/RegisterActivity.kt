package com.angelpr.losjardines.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.databinding.ActivityRegisterBinding
import com.angelpr.losjardines.ui.dialogFragment.DialogFragmentResponse
import com.angelpr.losjardines.ui.picker.GetPicker
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()
    private val getPicker = GetPicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        systemBar()

        // Events of stateFlow
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getPicker.pickerData.collect { uiDataPicker ->
                    if (uiDataPicker.dateValue.isNotEmpty()) {
                        binding.editDate.setText(uiDataPicker.dateValue)
                    }

                    if (uiDataPicker.hourValue.isNotEmpty()) {
                        binding.editHour.setText(uiDataPicker.hourValue)
                    }
                }
            }
        }

        // Events of setOnClickListener
        binding.editDate.setOnClickListener {
            getPicker.date(this)
        }

        binding.editHour.setOnClickListener {
            getPicker.hour(this)
        }

        binding.btnSave.setOnClickListener {
            sendDataOfView()
        }

        binding.btnConsultation.setOnClickListener {
            startActivity(Intent(this, ConsultationActivity::class.java))
        }

        // Event to back previous activity
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }

    }

    private fun sendDataOfView() {
        val collection = Calendar.getInstance().get(Calendar.YEAR).toString()

        val room = binding.editRoom.text.toString()
        val date = binding.editDate.text.toString()
        val hour = binding.editHour.text.toString()
        val name = binding.editName.text.toString()
        val dni = binding.editDni.text.toString()
        val price = binding.editPrice.text.toString()
        val origin = binding.editOrigin.text.toString()
        val observation = binding.editObservation.text.toString()

        //------Condicion de que los campos obligatorios no esten vacios-----
        val conditional = room.isEmpty().not() && date.isEmpty().not() && hour.isEmpty()
            .not() && name.isEmpty().not() && dni.isEmpty().not() && price.isEmpty()
            .not() && origin.isEmpty().not()

        if (conditional) {
            // Send data to server Firebase
            val clientInfoModel = ClientInfoModel(
                collection = collection,
                room = room.toInt(),
                date = date,
                hour = hour,
                name = name,
                dni = dni,
                price = price.toInt(),
                origin = origin,
                observation = observation
            )
            firebaseViewModel.sendData(clientInfoModel)

            firebaseViewModel.updateData(
                collection = "Rooms",
                documentPath = room,
                keyField = "state",
                updateData = false
            )
            DialogFragmentResponse(
                context = this,
                actionRegister = FirebaseViewModel.ActionRegister.SEND,
                firebaseViewModel = firebaseViewModel
            ).show(supportFragmentManager, "DialogFragmentResponse")
        } else {
            // Show Alert Dialog to fill all information except observation
            messageAlert()
        }
    }

    private fun messageAlert() {
        AlertDialog.Builder(this).apply {
            setTitle("Mensaje de Alerta")
            setMessage("Por favor, complete todos los campos obligatorios que se encuentran marcado con *")
            setCancelable(true)
            setPositiveButton("OK") { btn, _ -> btn.cancel() }
            show()
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
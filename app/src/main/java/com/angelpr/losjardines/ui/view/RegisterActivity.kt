package com.angelpr.losjardines.ui.view

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
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
import androidx.lifecycle.lifecycleScope
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.databinding.ActivityRegisterBinding
import com.angelpr.losjardines.ui.picker.GetPicker
import com.angelpr.losjardines.ui.viewmodel.ClientsViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val clientsViewModel: ClientsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        systemBar()

        // Events of liveData
        GetPicker.dateValue.observe(this) { date ->
            binding.editDate.setText(date)
        }

        GetPicker.hourValue.observe(this) { hour ->
            binding.editHour.setText(hour)
        }

        clientsViewModel.isSend.observe(this) { success ->
            if (success) {

            } else {

            }
        }

        // Events of setOnClickListener

        binding.editDate.setOnClickListener {
            GetPicker.date(this)
        }

        binding.editHour.setOnClickListener {
            GetPicker.hour(this)
        }

        binding.btnSave.setOnClickListener {
            sendDataOfView()
        }

        binding.btnConsultation.setOnClickListener {
            startActivity(Intent(this, ConsultationActivity::class.java))
        }

    }

    private fun sendDataOfView() {
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
            val clientInfo = Client(
                room = room.toInt(),
                date = date,
                hour = hour,
                name = name,
                dni = dni,
                price = price.toInt(),
                origin = origin,
                observation = observation
            )
            clientsViewModel.sendData(clientInfo)
            dialogView()
        } else {
            // Show Alert Dialog to fill all information except observation
            val txt =
                "Por favor, complete todos los campos obligatorios que se encuentran marcado con *"
            messageAlert(txt)
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

    private fun dialogView() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_box_status)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val textInfo = dialog.findViewById<TextView>(R.id.textInfo)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        val imageView = dialog.findViewById<ImageView>(R.id.stateIcon)

        progressBar.isGone = false
        imageView.isGone = true

        clientsViewModel.isSend.observe(this) { isSend ->
            progressBar.isGone = true
            imageView.isGone = false
            dialog.setCancelable(true) //Enable cancel when click outside

            if (isSend) {
                textInfo.text = getString(R.string.txt_state_succesfull)
                imageView.setImageResource(R.drawable.successful)
            } else {
               textInfo.text = getString(R.string.txt_state_error)
               imageView.setImageResource(R.drawable.error)
            }

        }

        dialog.show()


    }


    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
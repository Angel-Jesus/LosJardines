package com.angelpr.losjardines.ui.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.Months
import com.angelpr.losjardines.databinding.ActivityMainBinding
import com.angelpr.losjardines.ui.viewmodel.ClientsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val clientsViewModel : ClientsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        extracted()

        val client = Client(
            name = "Panduro Ruiz Angel Jesus",
            dni = "71605592",
            date = "24/06/2024",
            time = "12:00",
            observation = "",
            price = 45,
            origin = "Lamas",
            room = 203
        )

        clientsViewModel.isSend.observe(this){
            Toast.makeText(this, "Send: $it", Toast.LENGTH_SHORT).show()
        }

        clientsViewModel.clientRegisterData.observe(this){ clientRegister ->
            if(clientRegister.loading){
                Log.d("estado", "Loading")
            }
            else{
                Log.d("estado", "Finish")
                Log.d("estado", "data: ${clientRegister.clientsList}")
            }
        }

        binding.btnSend.setOnClickListener {
            clientsViewModel.sendData(clienInfo = client)
        }

        binding.btnGet.setOnClickListener {
            clientsViewModel.updateData("2024", "AJ4", "precio", 50)
        }



    }


    private fun extracted() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("estado", "onDestroy")
    }


}
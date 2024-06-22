package com.angelpr.losjardines.ui.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelpr.losjardines.R
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

        binding.btnSend.setOnClickListener {
            clientsViewModel.updateRegistertoFirebase()
        }

        binding.btnGet.setOnClickListener {
            clientsViewModel.deleteRegistertoFirebase()
        }

    }


    private fun extracted() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}
package com.angelpr.losjardines.ui.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelpr.losjardines.R
import com.angelpr.losjardines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        systemBar()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnConsultation.setOnClickListener {
            startActivity(Intent(this, ConsultationActivity::class.java))
        }

        binding.btnReservation.setOnClickListener {
            startActivity(Intent(this, ReservationActivity::class.java))
        }

        binding.btnProducts.setOnClickListener{
            startActivity(Intent(this, ProductsActivity::class.java))
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
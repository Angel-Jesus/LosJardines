package com.angelpr.losjardines.ui.view

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.StatiticsModel
import com.angelpr.losjardines.databinding.ActivityStatisticsBinding
import com.angelpr.losjardines.ui.viewmodel.ClientsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private val clientsViewModel: ClientsViewModel by viewModels()

    private val statisticsDays = listOf<StatiticsModel.Description>(
        StatiticsModel.Description("Day 1", 10),
        StatiticsModel.Description("Day 2", 20),
        StatiticsModel.Description("Day 3", 30),
        StatiticsModel.Description("Day 4", 40),
        StatiticsModel.Description("Day 5", 50),
        StatiticsModel.Description("Day 6", 60),
        StatiticsModel.Description("Day 7", 70),
        StatiticsModel.Description("Day 8", 80)
    )

    private val statisticsRoom = listOf<StatiticsModel.Description>(
        StatiticsModel.Description("H 101", 10),
        StatiticsModel.Description("H 102", 20),
        StatiticsModel.Description("H 103", 30),
        StatiticsModel.Description("H 104", 40),
        StatiticsModel.Description("H 105", 50),
        StatiticsModel.Description("H 106", 60),
        StatiticsModel.Description("H 107", 70),
        StatiticsModel.Description("H 108", 80)
    )

    private val statisticsCity = listOf<StatiticsModel.Description>(
        StatiticsModel.Description("SAN MARTIN", 10),
        StatiticsModel.Description("LIMA", 20),
        StatiticsModel.Description("LAMBAYEQUE", 30),
        StatiticsModel.Description("IQUITOS", 40),
        StatiticsModel.Description("PIURA", 50),
        StatiticsModel.Description("PUNO", 60),
        StatiticsModel.Description("EEUU", 70),
        StatiticsModel.Description("MOYOBAMBA", 80)
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        systemBar()

        // Add DialogViewLoading

        // Get Data lastMont to make statistics
        //clientsViewModel.getData(ClientsRegisterModel(filter = FilterType.lastMonth))

        // Events to LiveData
        /*
        clientsViewModel.statiticsCLientData.observe(this) { statiticsModel ->
            if(statiticsModel.statisticsDays.isNotEmpty())
            {
                createCharBarDays(statiticsModel.statisticsDays)
                //createCharBarRoom(statiticsModel.statisticsRoom)
                //createCharBarCity(statiticsModel.statisticsCity)
            }
        }
         */

        createCharBarDays(statisticsDays)

        // Event ToolBar action
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }


    }

    private fun createCharBarDays(statiticsDays: List<StatiticsModel.Description>) {

        val maxValue = statiticsDays.maxOf { it.value }
        val entrie = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for(index in statiticsDays.indices){
            entrie.add(BarEntry(index.toFloat(), statiticsDays[index].value.toFloat()))
            labels.add(statiticsDays[index].description)
        }

        binding.charBarDays.description.isEnabled = false
        binding.charBarDays.axisRight.setDrawLabels(false)

        val xAxis = binding.charBarDays.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.axisLineColor = R.color.black
        xAxis.axisLineWidth = 2f
        xAxis.labelCount = labels.size
        xAxis.labelRotationAngle = 315f

        val yAxis = binding.charBarDays.axisLeft
        yAxis.mAxisMaximum = (maxValue + 10).toFloat()
        yAxis.mAxisMinimum = 0f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = R.color.black
        yAxis.setDrawGridLines(false)
        yAxis.setDrawLabels(true)

        val dataSet = BarDataSet(entrie, "Cantidad de clientes")
        dataSet.setColor(Color.GREEN)
        dataSet.setDrawValues(true)

        val dataBar = BarData(dataSet)

        binding.charBarDays.data = dataBar
        binding.charBarDays.invalidate()

    }

    private fun createCharBarRoom(statisticsRoom: List<StatiticsModel.Description>) {

        val maxValue = statisticsRoom.maxOf { it.value }
        val entrie = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for(index in statisticsRoom.indices){
            entrie.add(BarEntry(index.toFloat(), statisticsRoom[index].value.toFloat()))
            labels.add(statisticsRoom[index].description)
        }


    }

    private fun createCharBarCity(statisticsCity: List<StatiticsModel.Description>) {

    }

    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
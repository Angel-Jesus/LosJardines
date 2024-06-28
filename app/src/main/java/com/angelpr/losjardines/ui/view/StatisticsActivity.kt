package com.angelpr.losjardines.ui.view

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.Months
import com.angelpr.losjardines.data.model.StatisticsModel
import com.angelpr.losjardines.databinding.ActivityStatisticsBinding
import com.angelpr.losjardines.ui.recycleView.StatisticsAdapter
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        systemBar()

        // Get Data lastMont to make statistics
        val calendar = Calendar.getInstance()
        val monthNow = calendar.get(Calendar.MONTH) + 1

        firebaseViewModel.getData(ClientsRegisterModel(filter = FilterType.lastMonth, timeFilter = genMonthStr(monthNow)))

        // Events to LiveData
        firebaseViewModel.statisticsClientData.observe(this) { statiticsModel ->
            if(statiticsModel.statisticsData.isNotEmpty())
            {
                createRecycleView(statiticsModel)
            }
        }

        // Event ToolBar action
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }


    }

    private fun createRecycleView(statisticsMolde: StatisticsModel) {

        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = StatisticsAdapter(statisticsMolde)
    }

    private fun genMonthStr(month: Int): Months{
        return when(month){
            1 -> Months.DECEMBER
            2 -> Months.JANUARY
            3 -> Months.FEBRUARY
            4 -> Months.MARCH
            5 -> Months.APRIL
            6 -> Months.MAY
            7 -> Months.JUNE
            8 -> Months.JULY
            9 -> Months.AUGUST
            10 -> Months.SEPTEMBER
            11 -> Months.OCTOBER
            else -> Months.NOVEMBER
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
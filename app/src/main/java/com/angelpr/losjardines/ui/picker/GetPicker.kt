package com.angelpr.losjardines.ui.picker

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GetPicker {

    private val _pickerData = MutableStateFlow<UiDataPicker>(UiDataPicker())
    val pickerData = _pickerData.asStateFlow()


    fun date(activity: AppCompatActivity){
        val datePicker = PickerDate{ dateStr -> _pickerData.update { it.copy(dateValue = dateStr)}}
        datePicker.show(activity.supportFragmentManager, "DatePicker")
    }

    fun hour(activity: AppCompatActivity){
        val timePicker = PickeTime{ hourStr -> _pickerData.update { it.copy(hourValue = hourStr) }}
        timePicker.show(activity.supportFragmentManager, "TimePicker")
    }

    data class UiDataPicker(
        val dateValue: String = "",
        val hourValue: String = ""
    )

}
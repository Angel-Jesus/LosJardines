package com.angelpr.losjardines.ui.picker

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

object GetPicker {
    val dateValue = MutableLiveData<String>()
    val hourValue = MutableLiveData<String>()

    fun date(activity: AppCompatActivity){
        val datePicker = PickerDate{ dateStr -> dateValue.postValue(dateStr)}
        datePicker.show(activity.supportFragmentManager, "DatePicker")
    }

    fun hour(activity: AppCompatActivity){
        val timePicker = PickeTime{ hourStr -> hourValue.postValue(hourStr)}
        timePicker.show(activity.supportFragmentManager, "TimePicker")
    }

}
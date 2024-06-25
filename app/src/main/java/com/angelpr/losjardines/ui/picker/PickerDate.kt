package com.angelpr.losjardines.ui.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment

class PickerDate(val callback: (String) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
        val monthStr = if ((month + 1) < 10) "0${month + 1}" else "${month + 1}"
        callback("$dayStr/$monthStr/$year")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return DatePickerDialog(activity as Context, this, year, month, day)
    }
}
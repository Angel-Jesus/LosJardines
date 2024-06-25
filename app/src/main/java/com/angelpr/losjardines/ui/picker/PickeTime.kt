package com.angelpr.losjardines.ui.picker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class PickeTime(val callback: (String) -> Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val hourStr = if (hourOfDay < 10) "0$hourOfDay" else hourOfDay
        val minuteStr = if (minute < 10) "0$minute" else minute
        return callback("$hourStr:$minuteStr")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(activity as Context, this, hour, minute, false)
    }
}
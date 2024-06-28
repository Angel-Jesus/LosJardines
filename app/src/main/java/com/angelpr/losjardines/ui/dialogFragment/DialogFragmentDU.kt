package com.angelpr.losjardines.ui.dialogFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.HeadNameDB
import com.angelpr.losjardines.data.model.UpdateDataModel
import com.angelpr.losjardines.ui.picker.GetPicker
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import com.google.android.material.textfield.TextInputEditText

class DialogFragmentDU(
    private val activity: AppCompatActivity,
    private val clienteViewModel: FirebaseViewModel,
    private var dataUpdate: UpdateDataModel
) : DialogFragment() {

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_fragment_option, container, false)

        val btnDelete = rootView.findViewById<Button>(R.id.btnDelete)
        val btnUpdate = rootView.findViewById<Button>(R.id.btnUpdate)
        val txtTitle = rootView.findViewById<TextView>(R.id.txtTitle)
        val editChanged = rootView.findViewById<TextInputEditText>(R.id.edit_changed)

        when (dataUpdate.keyField) {
            HeadNameDB.AYN_DB -> {
                txtTitle.text = "¿Desea cambiar el nombre y apellido?"
            }

            HeadNameDB.DNI_DB -> {
                txtTitle.text = "¿Desea cambiar el numero de DNI?"
            }

            HeadNameDB.DATE_DB -> {
                txtTitle.text = "¿Desea cambiar la fecha?"
                editChanged.isFocusable = false

                // Event to display a dialog picker of date
                editChanged.setOnClickListener {
                    GetPicker.date(activity)
                }

                GetPicker.dateValue.observe(activity) { date ->
                    editChanged.setText(date)
                }
            }

            HeadNameDB.TIME_DB -> {
                txtTitle.text = "¿Desea cambiar la hora?"
                editChanged.isFocusable = false
                // Event to display a dialog picker of time
                editChanged.setOnClickListener {
                    GetPicker.hour(activity)
                }

                GetPicker.hourValue.observe(activity) { hour ->
                    editChanged.setText(hour)
                }
            }

            HeadNameDB.OBSERVATION_DB -> {
                txtTitle.text = "¿Desea cambiar las observaciones?"
            }

            HeadNameDB.PRICE_DB -> {
                txtTitle.text = "¿Desea cambiar el precio?"
                editChanged.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }

            HeadNameDB.ORIGIN_DB -> {
                txtTitle.text = "¿Desea cambiar la procedencia?"
            }

            HeadNameDB.NUMER_ROOM_DB -> {
                txtTitle.text = "¿Desea cambiar el numero de habitacion?"
                editChanged.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }

            else -> {
                return rootView
            }
        }

        btnUpdate.setOnClickListener {
            dataUpdate.data = if (editChanged.text.toString().isNumer()) {
                editChanged.text.toString().toInt()
            } else {
                editChanged.text.toString()
            }
            //Log.d("estado", "actualizar : $dataUpdate")
            clienteViewModel.updateData(collection = dataUpdate.collection, documentPath = dataUpdate.documentPath, keyField = dataUpdate.keyField, updateData = dataUpdate.data)
            dismiss()
        }

        btnDelete.setOnClickListener {
            //Log.d("estado", "actualizar : $dataUpdate")
            clienteViewModel.deleteData(collection = dataUpdate.collection, documentPath = dataUpdate.documentPath)
            dismiss()
        }


        return rootView
    }

    private fun String.isNumer(): Boolean {
        return this.isDigitsOnly() && this.isNotEmpty()
    }
}
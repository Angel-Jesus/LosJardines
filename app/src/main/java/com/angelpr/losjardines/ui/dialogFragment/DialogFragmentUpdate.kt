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
import androidx.lifecycle.lifecycleScope
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.types.HeadNameDB
import com.angelpr.losjardines.data.model.UpdateDataModel
import com.angelpr.losjardines.ui.picker.GetPicker
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class DialogFragmentUpdate(
    private val activity: AppCompatActivity,
    private val clienteViewModel: FirebaseViewModel,
    private var dataUpdateModel: UpdateDataModel
) : DialogFragment() {

    private val getPicker = GetPicker()

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

        when (dataUpdateModel.keyField) {
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
                    getPicker.date(activity)
                }

                lifecycleScope.launch {
                    getPicker.pickerData.collect { uiDataPicker ->
                        editChanged.setText(uiDataPicker.dateValue)
                    }
                }

            }

            HeadNameDB.TIME_DB -> {
                txtTitle.text = "¿Desea cambiar la hora?"
                editChanged.isFocusable = false
                // Event to display a dialog picker of time
                editChanged.setOnClickListener {
                    getPicker.hour(activity)
                }

                lifecycleScope.launch {
                    getPicker.pickerData.collect { uiDataPicker ->
                        editChanged.setText(uiDataPicker.hourValue)
                    }
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
            dataUpdateModel.data = if (editChanged.text.toString().isNumer()) {
                editChanged.text.toString().toInt()
            } else {
                editChanged.text.toString()
            }
            //Log.d("estado", "actualizar : $dataUpdate")
            clienteViewModel.updateData(
                collection = dataUpdateModel.collection,
                documentPath = dataUpdateModel.documentPath,
                keyField = dataUpdateModel.keyField,
                updateData = dataUpdateModel.data
            )
            dismiss()
        }

        btnDelete.setOnClickListener {
            //Log.d("estado", "actualizar : $dataUpdate")
            clienteViewModel.deleteData(
                collection = dataUpdateModel.collection,
                documentPath = dataUpdateModel.documentPath
            )
            dismiss()
        }


        return rootView
    }

    private fun String.isNumer(): Boolean {
        return this.isDigitsOnly() && this.isNotEmpty()
    }
}
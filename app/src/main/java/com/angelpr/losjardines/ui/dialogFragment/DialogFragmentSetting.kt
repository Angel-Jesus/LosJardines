package com.angelpr.losjardines.ui.dialogFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.RoomModel
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import com.google.android.material.textfield.TextInputEditText

class DialogFragmentSetting(private val firebaseViewModel: FirebaseViewModel, private val roomList: List<RoomModel>): DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setView(requireActivity().layoutInflater.inflate(R.layout.dialog_fragment_setting, null))
            setPositiveButton("Actualizar precio", null)
            setNegativeButton("Cancelar"){btn,_ -> btn.cancel()}
        }
        val alertDialogBuilder = alertDialog.create()
        alertDialogBuilder.show()

        val positiveButton = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener{
            val roomEdit = dialog?.findViewById<TextInputEditText>(R.id.edit_room)
            val priceEdit = dialog?.findViewById<TextInputEditText>(R.id.edit_preciochange)
            val errorText = dialog?.findViewById<TextView>(R.id.txt_error)

            if (roomEdit?.text.toString().isEmpty() || priceEdit?.text.toString().isEmpty()) {
                // Error
                errorText?.visibility = View.VISIBLE
                errorText?.setText(R.string.txt_error_setting_empty)
            } else {
                val isRoomExist = roomList.find { it.roomNumber == roomEdit?.text.toString() }
                if(isRoomExist != null){
                    // Update data
                    firebaseViewModel.updateData(
                        collection = "Rooms",
                        documentPath = roomEdit!!.text.toString(),
                        keyField = "price",
                        updateData = priceEdit!!.text.toString().toInt()
                    )
                    alertDialogBuilder.cancel()
                }else{
                    errorText?.visibility = View.VISIBLE
                    errorText?.setText(R.string.txt_error_setting_room)
                }
            }
        }
        return alertDialogBuilder
    }

}
package com.angelpr.losjardines.ui.dialogFragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ActionProcess
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import kotlinx.coroutines.launch

class DialogFragmentResponse(
    private val context: Context,
    private val actionRegister: FirebaseViewModel.ActionRegister,
    private var firebaseViewModel: FirebaseViewModel
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_box_status)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val textInfo = dialog.findViewById<TextView>(R.id.textInfo)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        val imageView = dialog.findViewById<ImageView>(R.id.stateIcon)

        lifecycleScope.launch {
            firebaseViewModel.stateRegisterData.collect{uiStateRegister ->
                when(uiStateRegister.response){
                    ActionProcess.LOADING -> {
                        when(actionRegister){
                            FirebaseViewModel.ActionRegister.SEND -> textInfo.setText(R.string.txt_state_sending)
                            FirebaseViewModel.ActionRegister.UPDATE -> textInfo.setText(R.string.txt_state_update)
                            FirebaseViewModel.ActionRegister.DELETE -> textInfo.setText(R.string.txt_state_delete)
                            FirebaseViewModel.ActionRegister.GET -> textInfo.setText(R.string.txt_state_loading)
                        }
                    }
                    ActionProcess.SUCCESS -> {
                        dialog.setCancelable(true) //Enable cancel when click outside
                        progressBar.isGone = true
                        imageView.isGone = false
                        textInfo.text = getString(R.string.txt_state_succesfull)
                        imageView.setImageResource(R.drawable.successful)
                    }
                    ActionProcess.ERROR -> {
                        dialog.setCancelable(true) //Enable cancel when click outside
                        progressBar.isGone = true
                        imageView.isGone = false
                        textInfo.text = getString(R.string.txt_state_error)
                        imageView.setImageResource(R.drawable.error)
                    }
                }
            }
        }
        dialog.show()

        return dialog
    }
}
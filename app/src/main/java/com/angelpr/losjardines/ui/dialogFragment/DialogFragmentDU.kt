package com.angelpr.losjardines.ui.dialogFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.angelpr.losjardines.R
import com.google.android.material.textfield.TextInputEditText

class DialogFragmentDU: DialogFragment() {

    @SuppressLint("MissingInflatedId")
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

        

        return rootView
    }
}
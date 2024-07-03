package com.angelpr.losjardines.ui.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelpr.losjardines.R
import com.angelpr.losjardines.data.model.ActionProcess
import com.angelpr.losjardines.data.model.RoomModel
import com.angelpr.losjardines.databinding.ActivityRoomBinding
import com.angelpr.losjardines.ui.dialogFragment.DialogFragmentSetting
import com.angelpr.losjardines.ui.recycleView.RoomsAdapter
import com.angelpr.losjardines.ui.viewmodel.FirebaseViewModel
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        systemBar()

        firebaseViewModel.getRoomInfo()
        binding.progress.isGone = false
        binding.recycleView.isGone = true

        // Events of StateFlow
        lifecycleScope.launch {
            firebaseViewModel.stateRoomData.collect { uiStateRoom ->
                when (uiStateRoom.responseRoom) {
                    ActionProcess.LOADING -> {
                        binding.progress.isGone = false
                        binding.recycleView.isGone = true
                    }

                    ActionProcess.SUCCESS -> {
                        binding.progress.isGone = true
                        binding.recycleView.isGone = false
                        createRecyclerView(uiStateRoom.roomDataList)
                    }

                    ActionProcess.ERROR -> {
                        binding.recycleView.isGone = true
                        dialogViewError()

                    }
                }

                if(uiStateRoom.changeValue){
                    firebaseViewModel.getRoomInfo()
                }
            }

        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.setting -> {
                    val roomList = firebaseViewModel.stateRoomData.value.roomDataList
                    if (roomList.isNotEmpty()) {
                        DialogFragmentSetting(firebaseViewModel, roomList).show(
                            supportFragmentManager,
                            "DialogFragmentSetting"
                        )
                    }
                    true
                }

                else -> false
            }
        }

        // Event to back previous activity
        binding.toolbar.setNavigationOnClickListener {
            finish() // Return the previous screen and close the activity
        }
    }

    private fun createRecyclerView(roomList: List<RoomModel>) {
        binding.recycleView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycleView.layoutManager = linearLayoutManager
        binding.recycleView.adapter = RoomsAdapter(this, roomList) { room ->
            messageAlert(room)
        }
    }

    private fun messageAlert(room: RoomModel) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Mensaje: Actualización ")
        alert.setMessage("Esta por actualizar la disponibilidad de la habitación. ¿Desea continuar?")
        alert.setCancelable(true)
        alert.setPositiveButton("Actualizar") { btn, _ ->
            firebaseViewModel.updateData(
                collection = "Rooms",
                documentPath = room.roomNumber,
                keyField = "state",
                updateData = !room.roomState
            )
            btn.cancel()
        }
        alert.setNegativeButton("NO") { btn, _ ->
            btn.cancel()
        }
        alert.show()
    }

    private fun dialogViewError() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_box_status)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val textInfo = dialog.findViewById<TextView>(R.id.textInfo)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        val imageView = dialog.findViewById<ImageView>(R.id.stateIcon)

        progressBar.isGone = true
        imageView.isGone = false

        textInfo.text = getString(R.string.txt_state_error)
        imageView.setImageResource(R.drawable.error)

        dialog.show()
    }

    private fun systemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
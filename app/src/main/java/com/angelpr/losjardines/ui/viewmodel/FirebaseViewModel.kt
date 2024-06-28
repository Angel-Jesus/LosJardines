package com.angelpr.losjardines.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelpr.losjardines.data.model.ActionProcess
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.Months
import com.angelpr.losjardines.data.model.RoomModel
import com.angelpr.losjardines.data.model.StatisticsModel
import com.angelpr.losjardines.domain.DeleteDataToFirebase
import com.angelpr.losjardines.domain.GetDataToFirebase
import com.angelpr.losjardines.domain.GetRoomToFirebase
import com.angelpr.losjardines.domain.GetStatistics
import com.angelpr.losjardines.domain.SendDataToFirebase
import com.angelpr.losjardines.domain.UpdateDataToFirebase
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {

    private val sendDataToFirebase = SendDataToFirebase()
    private val getDataToFirebase = GetDataToFirebase()
    private val getStatistics = GetStatistics()
    private val getRoomToFirebase = GetRoomToFirebase()
    private val deleteDataToFirebase = DeleteDataToFirebase()
    private val updateDataToFirebase = UpdateDataToFirebase()

    val isSend = MutableLiveData<Boolean>()
    val isDelete = MutableLiveData<ActionProcess>()
    val isUpdate = MutableLiveData<ActionProcess>()
    val isStatistics = MutableLiveData<Boolean>()

    val clientRegisterData = MutableLiveData<ClientsRegisterModel>()
    val statisticsClientData = MutableLiveData<StatisticsModel>()
    val roomData = MutableLiveData<List<RoomModel>>()

    fun sendData(clienInfo: ClientInfoModel) {
        viewModelScope.launch {
            sendDataToFirebase(clienInfo) {
                isSend.postValue(it)
            }
        }
    }

    fun getData(clientsRegisterModel: ClientsRegisterModel) {
        viewModelScope.launch {
            // State of start loading
            //clientRegisterData.postValue(ClientsRegister(loading = true))
            isStatistics.postValue(false)
            getDataToFirebase(clientsRegister = clientsRegisterModel) { callback ->
                if (clientsRegisterModel.filter != FilterType.lastMonth) {
                    clientRegisterData.postValue(callback)
                } else {

                    statisticsClientData.postValue(
                        getStatistics(
                            month = getMonthStr(clientsRegisterModel.timeFilter),
                            clientsRegister = callback.clientsList
                        )
                    )
                    isStatistics.postValue(true)
                }

            }
        }
    }

    fun getRoomInfo(){
        viewModelScope.launch {
            getRoomToFirebase{roomList ->
                roomData.postValue(roomList)
            }
        }
    }

    fun deleteData(collection: String, documentPath: String) {
        viewModelScope.launch {
            isDelete.postValue(ActionProcess.LOADING)
            deleteDataToFirebase(collection = collection, documentPath = documentPath) { success ->
                val response = if (success) {
                    ActionProcess.SUCCESS
                } else {
                    ActionProcess.ERROR
                }
                isDelete.postValue(response)
            }
        }
    }

    fun updateData(collection: String, documentPath: String, keyField: String, updateData: Any) {
        viewModelScope.launch {
            isUpdate.postValue(ActionProcess.LOADING)
            updateDataToFirebase(
                collection = collection,
                documentPath = documentPath,
                keyField = keyField,
                data = updateData
            ) { success ->
                val response = if (success) {
                    ActionProcess.SUCCESS
                } else {
                    ActionProcess.ERROR
                }
                isUpdate.postValue(response)
            }
        }
    }

    private fun getMonthStr(month: Months): String {
        return when (month) {
            Months.JANUARY -> "Enero"
            Months.FEBRUARY -> "Febrero"
            Months.MARCH -> "Marzo"
            Months.APRIL -> "Abril"
            Months.MAY -> "Mayo"
            Months.JUNE -> "Junio"
            Months.JULY -> "Julio"
            Months.AUGUST -> "Agosto"
            Months.SEPTEMBER -> "Septiembre"
            Months.OCTOBER -> "Octubre"
            Months.NOVEMBER -> "Noviembre"
            else -> "Diciembre"
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("estado", "ViewModelScope onCleared")
    }

}
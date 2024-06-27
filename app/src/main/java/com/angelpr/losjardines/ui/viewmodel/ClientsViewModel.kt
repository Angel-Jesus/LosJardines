package com.angelpr.losjardines.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelpr.losjardines.data.model.ActionProcess
import com.angelpr.losjardines.data.model.ClientInfoModel
import com.angelpr.losjardines.data.model.ClientsRegisterModel
import com.angelpr.losjardines.data.model.FilterType
import com.angelpr.losjardines.data.model.StatiticsModel
import com.angelpr.losjardines.domain.DeleteDataToFirebase
import com.angelpr.losjardines.domain.GetDataToFirebase
import com.angelpr.losjardines.domain.GetStatistics
import com.angelpr.losjardines.domain.SendDataToFirebase
import com.angelpr.losjardines.domain.UpdateDataToFirebase
import kotlinx.coroutines.launch

class ClientsViewModel : ViewModel() {

    private val sendDataToFirebase = SendDataToFirebase()
    private val getDataToFirebase = GetDataToFirebase()
    private val deleteDataToFirebase = DeleteDataToFirebase()
    private val updateDataToFirebase = UpdateDataToFirebase()
    private val getStatistics = GetStatistics()

    val isSend = MutableLiveData<Boolean>()
    val isDelete = MutableLiveData<ActionProcess>()
    val isUpdate = MutableLiveData<ActionProcess>()
    val isStatistics = MutableLiveData<Boolean>()

    val clientRegisterData = MutableLiveData<ClientsRegisterModel>()
    val statiticsCLientData = MutableLiveData<StatiticsModel>()

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
                    statiticsCLientData.postValue(getStatistics(callback.clientsList))
                    isStatistics.postValue(true)
                }

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


    override fun onCleared() {
        super.onCleared()
        Log.d("estado", "ViewModelScope onCleared")
    }

}
package com.angelpr.losjardines.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelpr.losjardines.data.model.Client
import com.angelpr.losjardines.data.model.ClientsRegister
import com.angelpr.losjardines.domain.DeleteDataToFirebase
import com.angelpr.losjardines.domain.GetDataToFirebase
import com.angelpr.losjardines.domain.SendDataToFirebase
import com.angelpr.losjardines.domain.UpdateDataToFirebase
import kotlinx.coroutines.launch

class ClientsViewModel: ViewModel() {

    private val sendDataToFirebase = SendDataToFirebase()
    private val getDataToFirebase = GetDataToFirebase()
    private val deleteDataToFirebase = DeleteDataToFirebase()
    private val updateDataToFirebase = UpdateDataToFirebase()

    val isSend = MutableLiveData<Boolean>()
    val isDelete = MutableLiveData<Boolean>()
    val isUpdate = MutableLiveData<Boolean>()

    val clientRegisterData = MutableLiveData<ClientsRegister>()

    fun sendData(clienInfo: Client){
        viewModelScope.launch {
            sendDataToFirebase(clienInfo){
                isSend.postValue(it)
            }
        }
    }

    fun getData(clientsRegister: ClientsRegister){
        viewModelScope.launch {
             // State of start loading
            //clientRegisterData.postValue(ClientsRegister(loading = true))
            getDataToFirebase(clientsRegister = clientsRegister){ callback ->
                clientRegisterData.postValue(callback)
            }
        }
    }

    fun deleteData(collection: String, documentPath: String){
        viewModelScope.launch {
            deleteDataToFirebase(collection = collection, documentPath = documentPath){ success ->
                isDelete.postValue(success)
            }
        }
    }

    fun updateData(collection: String, documentPath: String, keyField: String, updateData: Any){
        viewModelScope.launch {
            updateDataToFirebase(collection = collection, documentPath = documentPath, keyField = keyField, data = updateData){ success ->
                isUpdate.postValue(success)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("estado", "ViewModelScope onCleared")
    }

}
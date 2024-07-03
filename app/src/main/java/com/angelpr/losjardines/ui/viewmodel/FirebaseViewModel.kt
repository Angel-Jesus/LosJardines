package com.angelpr.losjardines.ui.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {

    private val sendDataToFirebase = SendDataToFirebase()
    private val getDataToFirebase = GetDataToFirebase()
    private val getStatistics = GetStatistics()
    private val getRoomToFirebase = GetRoomToFirebase()
    private val deleteDataToFirebase = DeleteDataToFirebase()
    private val updateDataToFirebase = UpdateDataToFirebase()

    private val _stateRegisterData = MutableStateFlow(UiStateRegister())
    val stateRegisterData = _stateRegisterData.asStateFlow()

    private val _stateStatisticsData = MutableStateFlow(UiStateStatistics())
    val stateStatisticsData = _stateStatisticsData.asStateFlow()

    private val _stateRoomData = MutableStateFlow(UiStateRoom())
    val stateRoomData = _stateRoomData.asStateFlow()

    fun sendData(clienInfo: ClientInfoModel) {
        viewModelScope.launch {
            _stateRegisterData.update { it.copy(response = ActionProcess.LOADING) }
            // Send data to Firebase
            sendDataToFirebase(clienInfo) { response ->
                _stateRegisterData.update { it.copy(response = response) }
            }
        }
    }

    fun getData(clientsRegisterModel: ClientsRegisterModel) {
        viewModelScope.launch {
            // State of start loading
            _stateStatisticsData.update { it.copy(responseStatistic = ActionProcess.LOADING) }
            _stateRegisterData.update {
                it.copy(
                    response = ActionProcess.LOADING,
                    changeValue = false
                )
            }

            getDataToFirebase(clientsRegister = clientsRegisterModel) { callback, response ->
                if (clientsRegisterModel.filter != FilterType.lastMonth) {
                    _stateRegisterData.update {
                        it.copy(
                            clientRegisterModel = callback,
                            response = response
                        )
                    }
                } else {
                    _stateStatisticsData.update {
                        it.copy(
                            responseStatistic = response,
                            statisticsModel = getStatistics(
                                month = getMonthStr(clientsRegisterModel.timeFilter),
                                clientsRegister = callback.clientsList
                            )
                        )
                    }
                }

            }
        }
    }

    fun getRoomInfo() {
        viewModelScope.launch {
            _stateRoomData.update {
                it.copy(
                    responseRoom = ActionProcess.LOADING,
                    changeValue = false
                )
            }
            getRoomToFirebase { roomList, response ->
                _stateRoomData.update {
                    it.copy(
                        responseRoom = response,
                        roomDataList = roomList,
                        changeValue = false
                    )
                }
            }
        }
    }

    fun deleteData(collection: String, documentPath: String) {
        viewModelScope.launch {
            _stateRegisterData.update {
                it.copy(
                    response = ActionProcess.LOADING,
                    changeValue = false
                )
            }
            deleteDataToFirebase(collection = collection, documentPath = documentPath) { response ->
                if (response == ActionProcess.SUCCESS) {
                    _stateRegisterData.update { it.copy(response = response, changeValue = true) }
                } else {
                    _stateRegisterData.update { it.copy(response = response, changeValue = false) }
                }
            }
        }
    }

    fun updateData(collection: String, documentPath: String, keyField: String, updateData: Any) {
        viewModelScope.launch {
            _stateRegisterData.update {
                it.copy(
                    response = ActionProcess.LOADING,
                    changeValue = false
                )
            }
            _stateRoomData.update {
                it.copy(
                    responseRoom = ActionProcess.LOADING,
                    changeValue = false
                )
            }

            updateDataToFirebase(
                collection = collection,
                documentPath = documentPath,
                keyField = keyField,
                data = updateData
            ) { response ->
                if (response == ActionProcess.SUCCESS) {
                    _stateRegisterData.update { it.copy(response = response, changeValue = true) }
                    _stateRoomData.update {
                        it.copy(
                            responseRoom = ActionProcess.LOADING,
                            changeValue = true
                        )
                    }
                } else {
                    _stateRegisterData.update { it.copy(response = response, changeValue = false) }
                    _stateRoomData.update {
                        it.copy(
                            responseRoom = ActionProcess.LOADING,
                            changeValue = false
                        )
                    }
                }

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

    data class UiStateRegister(
        val response: ActionProcess = ActionProcess.LOADING,
        val changeValue: Boolean = false,
        val clientRegisterModel: ClientsRegisterModel = ClientsRegisterModel()
    )

    data class UiStateStatistics(
        val responseStatistic: ActionProcess = ActionProcess.LOADING,
        val statisticsModel: StatisticsModel = StatisticsModel()
    )

    data class UiStateRoom(
        val responseRoom: ActionProcess = ActionProcess.LOADING,
        val changeValue: Boolean = false,
        val roomDataList: List<RoomModel> = emptyList()
    )

    enum class ActionRegister {
        SEND,
        GET,
        UPDATE,
        DELETE
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("estado", "ViewModelScope onCleared")
    }

}
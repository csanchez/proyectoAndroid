package com.example.iisapp.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iisapp.data.NotificationsRepository
import com.example.iisapp.data.Result
import com.example.iisapp.data.SolicitudesRepository
import com.example.iisapp.data.model.IisNotification
import com.example.iisapp.data.model.Solicitud
import com.example.iisapp.ui.solicitudes.SolicitudesResult
import kotlinx.coroutines.launch

class NotificationsViewModel (private val notificationsRepository: NotificationsRepository) : ViewModel(){

    private val _notificationsResult = MutableLiveData<NotificationsResult>()
    val notificationsResult: LiveData<NotificationsResult> = _notificationsResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _failed = MutableLiveData<String>()
    val failed: LiveData<String>
        get() = _failed

    private val tag="NOTIFICATIONSVM"

    init {
        _loading.value = true
    }


    fun getNotifications( token: String ){


        viewModelScope.launch {

            val result = notificationsRepository.getNotifications(token)

            if (result is Result.Success) {

                _notificationsResult.value = NotificationsResult(success = result.data?.let { it as List<IisNotification> })


                _loading.value = false
            } else {
                if (result is Result.Error) { _loading.value = false
                    _notificationsResult.value = NotificationsResult(error = result.exception?.let { result.exception.message })
                }
            }
        }
    }

}
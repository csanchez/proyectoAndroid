package com.iis.app.ui.solicitudes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iis.app.data.Result
import com.iis.app.data.SolicitudesRepository
import com.iis.app.data.model.Solicitud
import kotlinx.coroutines.launch

class SolicitudesViewModel(private val solicitudesRepository: SolicitudesRepository) : ViewModel(){

    private val _solicitudesResult = MutableLiveData<SolicitudesResult>()
    val solicitudesResult: LiveData<SolicitudesResult> = _solicitudesResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _failed = MutableLiveData<String>()
    val failed: LiveData<String>
        get() = _failed

    private val tag="SOLICITUDESVM"

    init {
        _loading.value = true
    }


    fun getSolicitudes( token: String ){


        viewModelScope.launch {

            val result = solicitudesRepository.getSolicitudes(token)

            if (result is Result.Success) {

                _solicitudesResult.value = SolicitudesResult(success = result.data?.let { it as List<Solicitud> })


                _loading.value = false
            } else {
                if (result is Result.Error) { _loading.value = false
                    _solicitudesResult.value = SolicitudesResult(error = result.exception?.let { result.exception.message })
                }
            }
        }
    }

}
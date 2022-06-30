package com.iis.app.ui.tramites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iis.app.data.Result
import com.iis.app.data.TramitesRepository
import com.iis.app.data.model.Tramite
import kotlinx.coroutines.launch

class TramitesViewModel (private val tramitesRepository: TramitesRepository) : ViewModel(){

    private val _tramitesResult = MutableLiveData<TramitesResult>()
    val tramitesResult: LiveData<TramitesResult> = _tramitesResult

    private val _tramiteRegisteredResult = MutableLiveData<TramiteRegisteredResult>()
    val tramiteRegisteredResult: LiveData<TramiteRegisteredResult> = _tramiteRegisteredResult

    //private val _tramiteForm = MutableLiveData<TramiteFormState>()
    //val tramiteFormState: LiveData<LoginFormState> = _tramiteForm

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _failed = MutableLiveData<String>()
    val failed: LiveData<String>
        get() = _failed

    private val tag="TRAMITESVM"

    init {
        _loading.value = true
    }

    fun getTramite(position:Int): Tramite {
        return tramitesResult.value?.success!![position]
    }

    fun getTramites( tramiteType:String,token: String ){


        viewModelScope.launch {
            //val result = loginRepository.login(username, password,deviceId,deviceName,fcmToken)
            val result = tramitesRepository.getTramites(tramiteType,token)

            if (result is Result.Success) {

                _tramitesResult.value = TramitesResult(success = result.data?.let { it as List<Tramite> })
                _loading.value = false
            } else {
                if (result is Result.Error) {
                    _loading.value = false
                    _tramitesResult.value = TramitesResult(error = result.exception?.let { result.exception.message })
                }

                //_loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }



    fun registerToTramite( tramite: Tramite,token: String){

        //return ApiClient.getTramites(tramiteType)
        viewModelScope.launch {
            //val result = loginRepository.login(username, password,deviceId,deviceName,fcmToken)
            val result = tramitesRepository.registerToTramite(tramite,token)


            if (result is Result.Success) {
                _tramiteRegisteredResult.value = TramiteRegisteredResult(success = result.data?.let { it as String })
                _loading.value = false
            } else {
                if (result is Result.Error) {
                    _loading.value = false
                    _tramiteRegisteredResult.value = TramiteRegisteredResult(error = result.exception?.let { result.exception.message })
                }

                //_loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }
}
package com.example.iisapp.ui.tramites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iisapp.data.LoginRepository
import com.example.iisapp.data.Result
import com.example.iisapp.data.TramitesRepository
import com.example.iisapp.data.model.Data
import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.Tramite
import com.example.iisapp.rest.ApiClient
import com.example.iisapp.ui.login.LoginFormState
import com.example.iisapp.ui.login.LoginResult
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

    private val tag="LOGINVM"

    init {
        _loading.value = true
    }

    fun getTramite(position:Int): Tramite {
        Log.d(tag, "Success ${tramitesResult.value}")
        return tramitesResult.value?.success!![position]
    }

    fun getTramites( tramiteType:String,token: String ){

        //return ApiClient.getTramites(tramiteType)
        Log.d(tag, "tipo tramite $tramiteType")

        viewModelScope.launch {
            //val result = loginRepository.login(username, password,deviceId,deviceName,fcmToken)
            val result = tramitesRepository.getTramites(tramiteType,token)

            if (result is Result.Success) {

                Log.d(tag, "Success")
                Log.d(tag,  "result.data ${ result.data }" )
                _tramitesResult.value = TramitesResult(success = result.data?.let { it as List<Tramite> })
                Log.d(tag, "Success ${tramitesResult.value}")
                _loading.value = false
            } else {
                Log.d("LOGIN", "Error")
                if (result is Result.Error) {
                    Log.d(tag, "Error")
                    _loading.value = false
                    _tramitesResult.value = TramitesResult(error = result.exception?.let { result.exception.message })
                }

                //_loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }



    fun registerToTramite( tramite: Tramite,token: String){

        //return ApiClient.getTramites(tramiteType)
        Log.d(tag, "register to tramite ${tramite.name}")
        viewModelScope.launch {
            //val result = loginRepository.login(username, password,deviceId,deviceName,fcmToken)
            val result = tramitesRepository.registerToTramite(tramite,token)


            if (result is Result.Success) {

                Log.d(tag, "Success")
                Log.d(tag,  "result.data ${ result.data }" )
                _tramiteRegisteredResult.value = TramiteRegisteredResult(success = result.data?.let { it as String })
                Log.d(tag, "Success ${tramiteRegisteredResult.value}")
                _loading.value = false
            } else {
                Log.d("LOGIN", "Error")
                if (result is Result.Error) {
                    Log.d(tag, "Error")
                    _loading.value = false
                    _tramiteRegisteredResult.value = TramiteRegisteredResult(error = result.exception?.let { result.exception.message })
                }

                //_loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }
}
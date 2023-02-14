package com.iis.app.ui.reservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iis.app.data.ReservationsRepository
import com.iis.app.data.Result
import com.iis.app.data.model.Reservation
import kotlinx.coroutines.launch

class ReservationsViewModel(private val reservationsRepository: ReservationsRepository) : ViewModel(){

    private val _reservationsResult = MutableLiveData<ReservationsResult>()
    val reservationsResult: LiveData<ReservationsResult> = _reservationsResult




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


    fun getReservations( token: String ){


        viewModelScope.launch {

            val result = reservationsRepository.getReservations(token)

            if (result is Result.Success) {

                _reservationsResult.value = ReservationsResult(success = result.data?.let { it as List<Reservation> })


                _loading.value = false
            } else {
                if (result is Result.Error) { _loading.value = false
                    _reservationsResult.value = ReservationsResult(error = result.exception?.let { result.exception.message })
                }
            }
        }
    }
}
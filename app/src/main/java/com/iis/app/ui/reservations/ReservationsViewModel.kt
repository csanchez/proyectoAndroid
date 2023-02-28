package com.iis.app.ui.reservations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iis.app.data.ReservationsRepository
import com.iis.app.data.Result
import com.iis.app.data.model.Reservation
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class ReservationsViewModel(private val reservationsRepository: ReservationsRepository) : ViewModel(){

    private val _reservationsResult = MutableLiveData<ReservationsResult>()
    val reservationsResult: LiveData<ReservationsResult> = _reservationsResult


    //private var selectedDate: LocalDate? = null
    //private var selectedDate: LocalDate? = LocalDate.now();

    private val _selectedDate = MutableLiveData<LocalDate>()
    var selectedDate: LiveData<LocalDate> = _selectedDate

    var daysInMonthArray: ArrayList<String> = ArrayList()


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _failed = MutableLiveData<String>()
    val failed: LiveData<String>
        get() = _failed

    private val tag="ReservationsVM"

    init {
        _loading.value = true
    }

     fun filterEvents(selectedDate: LocalDate): ArrayList<Reservation> {
        var filteredEvents: ArrayList<Reservation> = ArrayList()


         reservationsResult.value?.success?.forEach{

             if (it.startDate.substring(0,2).toInt() == selectedDate.dayOfMonth) {
                 Log.d(tag,selectedDate.toString()+ " - "+ it.name)
                 filteredEvents.add(it)
             }
         }
         return filteredEvents
    }

    fun getSelectedDate(): LocalDate? {
        return selectedDate.value
    }


    fun getReservations(start: String,end: String,space: String,user: String,event: String,service: String, requireEquipo: String, token: String ){

        Log.d("VIEW model ", "getReservations ")
        viewModelScope.launch {

            val result = reservationsRepository.getReservations(start,end,space,user,event,service,requireEquipo,token)

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


    //private fun daysInMonthArray(date: LocalDate): ArrayList<String>? {
     fun daysInMonthArray(): ArrayList<String>? {
        Log.d("VIEWMODELS", "daysInMonthArray")
        daysInMonthArray = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(_selectedDate.value)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        //val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val firstOfMonth = _selectedDate.value?.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth!!.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    fun previousMonthAction() {
        Log.d("VIEWMODELS","previousMonthAction")
        //selectedDate = selectedDate!!.minusMonths(1)
        _selectedDate.value =  _selectedDate.value?.minusMonths(1)
    }

    fun nextMonthAction() {
        Log.d("VIEWMODELS","nextMonthAction")
        _selectedDate.value =  _selectedDate.value?.plusMonths(1)
    }

    fun initDate() {
        Log.d("VIEWMODELS","initDate")
        _selectedDate.value =  LocalDate.now();
    }



    fun showDayArray(): List<String>? {


        Log.d("VIEWMODELS","showDayArray")
        //val showDayArray: ArrayList<Boolean> = ArrayList(this.daysInMonthArray.count())
        val showDayArray: ArrayList<String> = ArrayList(this.daysInMonthArray.count())

        reservationsResult.value?.success?.forEach{
            Log.d("VIEWMODELS", it.startDate+"")
            showDayArray.add(it.startDate.substring(0,2))
            //Log.d("VIEWMODEL",it.startDate)
            //Log.d("VIEWMODEL",it.startDate.substring(0,2))
        }
        return showDayArray.distinct()
    }

}
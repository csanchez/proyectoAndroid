package com.iis.app.data

import com.iis.app.data.model.Reservation


class ReservationsRepository(val dataSource: ReservationsDataSource)  {

    var reservations: List<Reservation>? = null
        private set



    suspend fun getReservations(start: String,end: String,space: String,user: String,event: String,service: String, requireEquipo: String,token: String):  Result<Any> {
        // handle login

        val result = dataSource.getReservations(start,end,space,user,event,service,requireEquipo,token)

        if (result is Result.Success) {
            result.data?.let { setReservations(it as List<Reservation>) }
        }

        return result
    }




    private fun setReservations(reservations: List<Reservation>) {
        this.reservations = reservations
    }


}
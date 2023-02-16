package com.iis.app.data



import com.iis.app.rest.ApiClient

class ReservationsDataSource {

    suspend fun getReservations(start: String,end: String,space: String,user: String,event: String,service: String, requireEquipo: String,token: String): Result<Any> {
        return ApiClient.getReservations(start,end,space,user,event,service,requireEquipo,token)
    }


}
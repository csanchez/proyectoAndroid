package com.iis.app.data



import com.iis.app.rest.ApiClient

class ReservationsDataSource {

    suspend fun getReservations(token: String): Result<Any> {
        return ApiClient.getReservations(token)
    }


}
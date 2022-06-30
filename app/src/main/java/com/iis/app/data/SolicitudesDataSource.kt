package com.iis.app.data

import com.iis.app.rest.ApiClient

class SolicitudesDataSource {



    suspend fun getSolicitudes(token: String): Result<Any> {
        return ApiClient.getSolicitudes(token)
    }
}
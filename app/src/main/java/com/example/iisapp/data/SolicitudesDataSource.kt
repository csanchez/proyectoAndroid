package com.example.iisapp.data

import com.example.iisapp.data.Result
import com.example.iisapp.rest.ApiClient

class SolicitudesDataSource {



    suspend fun getSolicitudes(token: String): Result<Any> {
        return ApiClient.getSolicitudes(token)
    }
}
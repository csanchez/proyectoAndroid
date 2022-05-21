package com.example.iisapp.data

import com.example.iisapp.data.model.Tramite
import com.example.iisapp.data.model.UserCredentials
import com.example.iisapp.rest.ApiClient

class TramitesDataSource {

    suspend fun getTramites(tipoTramite: String,token: String): Result<Any> {
        return ApiClient.getTramites(tipoTramite,token)
    }

    suspend fun registerToTramite(tramite: Tramite,token: String): Result<Any> {
        return ApiClient.registerToTramite(tramite,token)
    }
}
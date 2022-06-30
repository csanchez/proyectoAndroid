package com.iis.app.data

import com.iis.app.data.model.Tramite
import com.iis.app.rest.ApiClient

class TramitesDataSource {

    suspend fun getTramites(tipoTramite: String,token: String): Result<Any> {
        return ApiClient.getTramites(tipoTramite,token)
    }

    suspend fun registerToTramite(tramite: Tramite,token: String): Result<Any> {
        return ApiClient.registerToTramite(tramite,token)
    }
}
package com.example.iisapp.data

import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.Tramite
import com.example.iisapp.data.model.UserCredentials

class TramitesRepository(val dataSource: TramitesDataSource) {

    var tramites: List<Tramite>? = null
        private set
    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        tramites =  listOf<Tramite>()
    }

    suspend fun getTramites(tipoTramite: String,token: String):  Result<Any> { // Result<LoggedInUser>? {
        // handle login

        val result = dataSource.getTramites(tipoTramite,token)

        if (result is Result.Success) {
            result.data?.let { setTramites(it as List<Tramite>) }
        }

        return result
    }

    private fun setTramites(tramites: List<Tramite>) {
        this.tramites = tramites
    }
}


package com.iis.app.data

import com.iis.app.data.model.*

class TramitesRepository(val dataSource: TramitesDataSource) {

    var tramites: List<Tramite>? = null
        private set
    var tramite: Tramite? = null
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

    suspend fun registerToTramite(tramite: Tramite,token: String):  Result<Any> { // Result<LoggedInUser>? {
        // handle login

        val result = dataSource.registerToTramite(tramite,token)

        /*if (result is Result.Success) {

            result.data?.let { Log.d("TramitesRepository" , it.toString()) }
            result.data?.let { Log.d("TramitesRepository" , it) }
        }*/

        return result
    }

    private fun setTramites(tramites: List<Tramite>) {
        this.tramites = tramites
    }
    private fun setTramite(tramite: Tramite) {
        this.tramite = tramite
    }
}


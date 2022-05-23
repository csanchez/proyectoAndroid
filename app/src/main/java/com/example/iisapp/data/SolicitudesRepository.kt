package com.example.iisapp.data

import com.example.iisapp.data.model.Solicitud
import com.example.iisapp.data.model.Tramite

class SolicitudesRepository(val dataSource: SolicitudesDataSource) {

    var solicitudes: List<Solicitud>? = null
        private set

    suspend fun getSolicitudes(token: String):  Result<Any> { // Result<LoggedInUser>? {
        // handle login

        val result = dataSource.getSolicitudes(token)

        if (result is Result.Success) {
            result.data?.let { setSolicitudes(it as List<Solicitud>) }
        }

        return result
    }

    private fun setSolicitudes(solicitudes: List<Solicitud>) {
        this.solicitudes = solicitudes
    }
}
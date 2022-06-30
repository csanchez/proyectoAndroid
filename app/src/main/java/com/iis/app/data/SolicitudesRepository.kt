package com.iis.app.data

import com.iis.app.data.model.Solicitud

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
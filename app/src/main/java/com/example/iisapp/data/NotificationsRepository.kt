package com.example.iisapp.data

import com.example.iisapp.data.model.IisNotification
import com.example.iisapp.data.model.Solicitud

class NotificationsRepository(val dataSource: NotificationsDataSource)  {

    var notifications: List<IisNotification>? = null
        private set

    suspend fun getNotifications(token: String):  Result<Any> {
        // handle login

        val result = dataSource.getNotifications(token)

        if (result is Result.Success) {
            result.data?.let { setSolicitudes(it as List<IisNotification>) }
        }

        return result
    }

    private fun setSolicitudes(solicitudes: List<IisNotification>) {
        this.notifications = solicitudes
    }
}
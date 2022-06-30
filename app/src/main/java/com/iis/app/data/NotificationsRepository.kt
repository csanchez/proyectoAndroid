package com.iis.app.data

import com.iis.app.data.model.IisNotification

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

    suspend fun markAsSeen(id:Int, token: String): Result<Any> {
        val result = dataSource.markAsSeen(id, token)

       /* if (result is Result.Success) {
            result.data?.let { setSolicitudes(it as List<IisNotification>) }
        }*/

        return result
    }




    private fun setSolicitudes(solicitudes: List<IisNotification>) {
        this.notifications = solicitudes
    }


}
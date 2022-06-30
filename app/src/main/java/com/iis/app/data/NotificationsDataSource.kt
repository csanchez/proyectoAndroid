package com.iis.app.data

import com.iis.app.rest.ApiClient

class NotificationsDataSource {

    suspend fun getNotifications(token: String): Result<Any> {
        return ApiClient.getNotifications(token)
    }

    suspend fun markAsSeen(id:Int, token: String): Result<Any> {
        return ApiClient.markAsSeen(id,token)
    }
}
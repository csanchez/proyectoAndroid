package com.example.iisapp.data

import com.example.iisapp.rest.ApiClient

class NotificationsDataSource {

    suspend fun getNotifications(token: String): Result<Any> {
        return ApiClient.getNotifications(token)
    }
}
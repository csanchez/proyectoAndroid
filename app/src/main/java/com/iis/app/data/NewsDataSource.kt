package com.iis.app.data

import com.iis.app.rest.ApiClient

class NewsDataSource {
    suspend fun getNews(token: String): Result<Any> {
        return ApiClient.getNews(token)
    }
}
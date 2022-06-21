package com.example.iisapp.data

import com.example.iisapp.rest.ApiClient

class NewsDataSource {
    suspend fun getNews(token: String): Result<Any> {
        return ApiClient.getNews(token)
    }
}
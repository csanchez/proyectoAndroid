package com.example.iisapp.ui.home

import com.example.iisapp.data.model.New


data class NewsResult(
    val success: List<New>? = null,
    val error: String?=null
)

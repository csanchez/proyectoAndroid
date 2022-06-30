package com.iis.app.ui.home

import com.iis.app.data.model.New


data class NewsResult(
    val success: List<New>? = null,
    val error: String?=null
)

package com.example.iisapp.ui.tramites

import com.example.iisapp.data.model.Tramite

data class TramitesResult(
    val success: List<Tramite>? = null,
    val error: String?=null
)

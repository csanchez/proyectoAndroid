package com.iis.app.ui.tramites

import com.iis.app.data.model.Tramite

data class TramitesResult(
    val success: List<Tramite>? = null,
    val error: String?=null
)

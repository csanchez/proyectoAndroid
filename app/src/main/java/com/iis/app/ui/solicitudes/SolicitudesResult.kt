package com.iis.app.ui.solicitudes

import com.iis.app.data.model.Solicitud


data class SolicitudesResult(
    val success: List<Solicitud>? = null,
    val error: String?=null
)

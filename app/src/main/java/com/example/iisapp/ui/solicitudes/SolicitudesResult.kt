package com.example.iisapp.ui.solicitudes

import com.example.iisapp.data.model.Solicitud


data class SolicitudesResult(
    val success: List<Solicitud>? = null,
    val error: String?=null
)

package com.iis.app.rest

import com.iis.app.data.model.Solicitud
import com.google.gson.annotations.SerializedName

data class SolicitudesResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("solicitudes") var solicitudes: List<Solicitud>
)

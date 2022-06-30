package com.iis.app.rest

import com.iis.app.data.model.Tramite
import com.google.gson.annotations.SerializedName

data class TramitesResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("tramites") var tramites: List<Tramite>
)

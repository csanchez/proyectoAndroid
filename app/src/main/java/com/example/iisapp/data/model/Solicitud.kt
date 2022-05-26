package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

data class Solicitud(
    @SerializedName("tramiteId") var tramiteSlug: Int,
    @SerializedName("tramiteSlug") var tramiteId: String,
    @SerializedName("tramiteName") var tramiteName: String,
    @SerializedName("tipoTramite") var tipoTramite: String,
    @SerializedName("status") var status: String,
    @SerializedName("tramiteUserId") var tramiteUserId: String,
    @SerializedName("startedAt") var startedAt: String,
    @SerializedName("current_tramite_step_user") var currentStep: Step
)



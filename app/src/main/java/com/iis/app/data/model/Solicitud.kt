package com.iis.app.data.model

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Solicitud(
    @SerializedName("tramiteId") var tramiteId: Int,
    @SerializedName("tramiteSlug") var tramiteSlug: String,
    @SerializedName("tramiteName") var tramiteName: String,
    @SerializedName("tipoTramite") var tipoTramite: String,
    @SerializedName("status") var status: String,
    @SerializedName("statusName") var statusName: String,
    @SerializedName("tramiteUserId") var tramiteUserId: String,
    @SerializedName("startedAt") var startedAt: String,
    @SerializedName("current_tramite_step_user") var currentStep: Step,
    @SerializedName("totalSteps") var totalSteps: Int,
    @SerializedName("currentStep") var currentStepNumber: Int,
    @SerializedName("progress") var progress: Int,

    @SerializedName("departments") var departments: String,
    @SerializedName("departmentInitial") var departmentInitial: String,
    @SerializedName("departmentColor") var departmentColor: String,
    @SerializedName("tramiteUserNumber") var tramiteUserNumber: String



)



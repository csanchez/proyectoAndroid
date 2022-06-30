package com.iis.app.rest

import com.google.gson.annotations.SerializedName

data class TramiteRegisteredResponse(
    @SerializedName("message") var message: String,
)

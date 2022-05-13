package com.example.iisapp.rest

import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.Tramite
import com.google.gson.annotations.SerializedName

data class TramitesResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("tramites") var tramites: List<Tramite>
)

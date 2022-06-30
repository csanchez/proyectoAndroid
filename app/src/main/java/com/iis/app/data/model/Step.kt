package com.iis.app.data.model

import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("state") var state: String,
    @SerializedName("name") var name: String,
    @SerializedName("descripcion") var descripcion: String,
    @SerializedName("conditions") var conditions: List<Condition>
)

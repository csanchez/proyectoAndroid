package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("condition_type") var conditionType: String,
    @SerializedName("instruction") var instruction: String,
    @SerializedName("completed") var completed: Boolean,
    @SerializedName("completed_at") var completedAt: String,
    @SerializedName("value") var value: String,
    @SerializedName("iis_role") var iisRole: String
)

package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

data class Department(
    @SerializedName("name") var name: String,
    @SerializedName("department_type") var departmentType: String,
)



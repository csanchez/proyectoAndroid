package com.iis.app.data.model

import com.google.gson.annotations.SerializedName

data class Tramite (


    @SerializedName("id") var id: Int,
    @SerializedName("slug") var slug: String,
    @SerializedName("name") var name: String,
    @SerializedName("descripcion") var descripcion: String,
    @SerializedName("instructions") var instructions: String,
    @SerializedName("tramite_type") var tramiteType: String,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: List<Data>,
    @SerializedName("departments") var departments: List<Department>,
    @SerializedName("color") var color: String
    )





package com.iis.app.rest

import com.google.gson.annotations.SerializedName
import com.iis.app.data.model.Reservation


data class ReservationsResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("reservations") var reservations: List<Reservation>
)
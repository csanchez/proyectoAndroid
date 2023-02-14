package com.iis.app.ui.reservations

import com.iis.app.data.model.Reservation


data class ReservationsResult(
    val success: List<Reservation>? = null,
    val error: String?=null
)
package com.iis.app.data.model

import com.google.gson.annotations.SerializedName

data class Reservation(
    @SerializedName("start_date") var startDate: String,
    @SerializedName("end_date") var endDate: String,
    @SerializedName("start_time") var startTime: String,
    @SerializedName("end_time") var endTime: String,
    @SerializedName("title") var title: String,
    @SerializedName("name") var name: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("user_name") var userName: String,
    @SerializedName("created_by_name") var createdByName: String,
    @SerializedName("space_name") var spaceName: String,
    @SerializedName("event_id") var eventId: Int,
    @SerializedName("reservation_id") var reservationId: Int,
    @SerializedName("user_id") var userId: Int,
    @SerializedName("can_see") var canSee: Boolean,
    @SerializedName("space") var space: String,
    @SerializedName("color") var color: String,
    @SerializedName("event_type") var eventType: String,
    @SerializedName("number_of_attendants") var numberOfAttendants: Int,
    @SerializedName("number_of_virtual_attendants") var numberOfVirtualAttendants: Int,
    @SerializedName("numero_solicitud") var numeroSolicitud: String,
    @SerializedName("description") var description: String,
    @SerializedName("youtube_type") var youtubeType: String,
    @SerializedName("youtube_canal") var youtubeCanal: String,
    @SerializedName("with_youtube") var withYoutube: Boolean,
    @SerializedName("with_zoom") var withZoom: Boolean,
    @SerializedName("zoom_canal") var zoomCanal: String,
    @SerializedName("status") var status: String

)



package com.example.otaqtk.pojo

import com.beust.klaxon.Json

data class Rating(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "profile_uid")
    val ProfileUID: String,
    @Json(name = "series_cid")
    val SeriesCID: String,
    @Json(name = "rate")
    val Rate: Float
) {
}
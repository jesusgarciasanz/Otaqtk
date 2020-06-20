package com.example.otaqtk.pojo

import com.beust.klaxon.Json

data class Chapter(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "cid")
    val CID: String,
    @Json(name = "series_cid")
    val SeriesCID: String,
    @Json(name = "cover")
    val Cover: String,
    @Json(name = "title")
    val Title: String,
    @Json(name = "description")
    val Description: String,
    @Json(name = "series")
    val Series: Series,
    @Json(name = "rating")
    val Rating: Float
) {
}
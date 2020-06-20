package com.example.otaqtk.pojo

import com.beust.klaxon.Json

data class Series(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "cid")
    val CID: String,
    @Json(name = "type")
    val Type: String,
    @Json(name = "cover")
    val Cover:String,
    @Json(name = "title")
    val Title: String,
    @Json(name = "rating")
    val Rating: Float
    ) {
}
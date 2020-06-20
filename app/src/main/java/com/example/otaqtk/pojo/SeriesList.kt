package com.example.otaqtk.pojo

import com.beust.klaxon.Json
import java.util.*

data class SeriesList(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "list_id")
    val ListID: String,
    @Json(name = "series_cid")
    val SeriesCID: String
    ) {
}
package com.example.otaqtk.kitsu_pojo

import com.squareup.moshi.Json

@Json(name = "data")
data class Data(
    @Json(name = "id")
    val id: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "links")
    val links: Links,
    @Json(name = "attributes")
    val attributes: Attributes
) {
}
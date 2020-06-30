package com.example.otaqtk.kitsu_pojo

import com.squareup.moshi.Json

data class Included(
    @Json(name = "included")
    val included: List<Data>
) {
}
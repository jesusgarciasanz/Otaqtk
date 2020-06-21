package com.example.otaqtk.kitsu_pojo

import com.squareup.moshi.Json

data class DataClass(
    @Json(name = "data")
    val data: Data
) {

}
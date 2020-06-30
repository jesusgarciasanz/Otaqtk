package com.example.otaqtk.kitsu_pojo

import com.squareup.moshi.Json

@Json(name = "data")
class DataArray(
    @Json(name = "data")
    val data: List<Data>,
    @Json(name = "links")
    val links: Links

) {

}
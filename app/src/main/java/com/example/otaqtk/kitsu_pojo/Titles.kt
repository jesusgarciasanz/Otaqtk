package com.example.otaqtk.kitsu_pojo
import com.beust.klaxon.*

data class Titles (
    @Json(name = "en")
    val en: String,

    @Json(name = "en_jp")
    val enJp: String,

    @Json(name = "en_us")
    val enUs: String
)
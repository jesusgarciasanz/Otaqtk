package com.example.otaqtk.kitsu_pojo
import com.beust.klaxon.*

data class Titles (
    val en: Any? = null,

    @Json(name = "en_jp")
    val enJp: String,

    @Json(name = "en_us")
    val enUs: String
)
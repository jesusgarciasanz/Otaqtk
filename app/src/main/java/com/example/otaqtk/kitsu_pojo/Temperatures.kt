package com.example.otaqtk.kitsu_pojo
import com.beust.klaxon.*

private val klaxon = Klaxon()
data class Temperatures (
    val data: Data
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Temperatures>(json)
    }
}
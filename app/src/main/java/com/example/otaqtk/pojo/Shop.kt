package com.example.otaqtk.pojo

import com.beust.klaxon.Json

data class Shop(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "chapter_cid")
    val ChapterCID: String,
    @Json(name = "name")
    val Name: String,
    @Json(name = "price")
    val Price: Float,
    @Json(name = "url")
    val Url: String,
    @Json(name = "is_active")
    val IsActive: Boolean
) {
}
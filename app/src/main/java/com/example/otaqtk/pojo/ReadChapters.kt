package com.example.otaqtk.pojo

import com.beust.klaxon.Json

data class ReadChapters(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "profile_uid")
    val ProfileUID: String,
    @Json(name = "chapter_cid")
    val ChapterCID: String
) {
}
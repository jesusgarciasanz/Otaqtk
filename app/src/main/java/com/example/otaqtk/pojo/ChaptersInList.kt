package com.example.otaqtk.pojo

import com.beust.klaxon.Json

data class ChaptersInList(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "list_id")
    val ListID: String,
    @Json(name = "chapter_cid")
    val ChapterCID: String
) {
}
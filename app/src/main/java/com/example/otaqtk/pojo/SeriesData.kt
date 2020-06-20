package com.example.otaqtk.pojo

import com.beust.klaxon.*

data class SeriesData(
    @Json(name = "is_collecting")
    var isCollecting: Boolean,
    @Json(name = "is_favourite")
    val IsFavourite: Boolean,
    @Json(name = "followed_chapters")
    val FollowedChapters: Int,
    @Json(name = "chapters_cid")
    val ChaptersCID: String,
    @Json(name = "rating")
    val Rating: Float
) {
}
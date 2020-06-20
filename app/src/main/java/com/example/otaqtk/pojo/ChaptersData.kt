package com.example.otaqtk.pojo
import com.beust.klaxon.*
data class ChaptersData(
    @Json(name = "owned")
    val Owned: Boolean,
    @Json(name = "is_read")
    val IsRead: Boolean,
    @Json(name="wanted")
    val Wanted: Boolean,
    @Json(name="followed_chapters")
    val FollowedChapters: Int,
    @Json(name="chapters_cid")
    val ChaptersCID: String,
    @Json(name="shops")
    val Shops: Shop
) {
}
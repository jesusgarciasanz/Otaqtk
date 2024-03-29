package com.example.otaqtk.kitsu_pojo

import com.squareup.moshi.Json

data class Attributes(
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "updatedAt")
    val updatedAt: String?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "synopsis")
    val synopsis: String?,
    @Json(name = "coverImageTopOffset")
    val coverImageTopOffset: Long?,
    @Json(name = "titles")
    val titles: Titles?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "canonicalTitle")
    val canonicalTitle: String?,
    @Json(name = "abbreviatedTitles")
    val abbreviatedTitles: ArrayList<String>?,
    @Json(name = "averageRating")
    val averageRating: String?,
    @Json(name = "userCount")
    val userCount: Long?,
    @Json(name = "favoritesCount")
    val favoritesCount: Long?,
    @Json(name = "startDate")
    val startDate: String?,
    @Json(name = "endDate")
    val endDate: String?,
    @Json(name = "nextRelease")
    val nextRelease: String?,
    @Json(name = "popularityRank")
    val popularityRank: Long?,
    @Json(name = "ratingRank")
    val ratingRank: Long?,
    @Json(name = "ageRating")
    val ageRating: String?,
    @Json(name = "ageRatingGuide")
    val ageRatingGuide: String?,
    @Json(name = "subtype")
    val subtype: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "tba")
    val tba: String?,
    @Json(name = "posterImage")
    val posterImage: PosterImage?,
    @Json(name = "coverImage")
    val coverImage: CoverImage?,
    @Json(name = "chapterCount")
    val chapterCount: Long?,
    @Json(name = "volumeCount")
    val volumeCount: Long?,
    @Json(name = "serialization")
    val serialization: String?,
    @Json(name = "mangaType")
    val mangaType: String?,
    @Json(name = "thumbnail")
    val thumbnail : PosterImage?,
    @Json (name = "number")
    val number: Int?,
    @Json(name = "published")
    val published: String?,
    @Json (name = "airdate")
    val airdate: String?,
    @Json(name = "seasonNumber")
    val seasonNunber: Int?,
    @Json(name = "volumeNumber")
    val volumeNumber: Int?,
    @Json(name = "length")
    val length: String?
)
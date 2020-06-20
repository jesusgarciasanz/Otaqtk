package com.example.otaqtk.kitsu_pojo

import com.squareup.moshi.Json

class CategoryAttributes(
    val createdAt: String,
    val updatedAt: String,
    @Json(name = "title")
    val title: String,
    val description: String,
    val totalMediaCount: Long,
    val slug: String,
    val nsfw: Boolean,
    val childCount: Long,
    val image: String
) {

}
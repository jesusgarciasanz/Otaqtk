package com.example.otaqtk.pojo
import com.beust.klaxon.*
data class List(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "profile_uid")
    val ProfileUID: String,
    @Json(name = "profile")
    val Profile: Profile,
    @Json(name = "name")
    val Name: String,
    @Json(name = "is_public")
    val IsPublic: Boolean,
    @Json(name = "is_default")
    val IsDefault: Boolean,
    @Json(name = "is_internal")
    val IsInternal: Boolean,
    @Json(name = "series")
    val Series : Series

) {
}
package com.example.otaqtk.pojo
import com.beust.klaxon.*

data class Profile(
    @Json(name = "id")
    val ID: String?,
    @Json(name = "created_at")
    val CreatedAt: String?,
    @Json(name = "device_id")
    val device_id: String,
    @Json(name = "profile_image")
    val profile_image: String?,
    @Json(name = "cover_image")
    val CoverImage: String?,
    @Json(name = "is_active")
    val IsActive: Boolean?,
    @Json(name = "username")
    val username: String
) {
}
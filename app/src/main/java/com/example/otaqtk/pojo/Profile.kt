package com.example.otaqtk.pojo
import com.beust.klaxon.*

data class Profile(
    @Json(name = "id")
    val ID: String,
    @Json(name = "created_at")
    val CreatedAt: String,
    @Json(name = "uid")
    val UID: String,
    @Json(name = "device_id")
    val DeviceId: String,
    @Json(name = "profile_image")
    val ProfileImage: String,
    @Json(name = "cover_image")
    val CoverImage: String,
    @Json(name = "is_active")
    val IsActive: Boolean
) {
}
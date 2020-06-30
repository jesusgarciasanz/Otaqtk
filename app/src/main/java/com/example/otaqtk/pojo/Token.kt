package com.example.otaqtk.pojo

import com.squareup.moshi.Json

data class Token(
    @Json(name = "token")
    val token: String) {
}
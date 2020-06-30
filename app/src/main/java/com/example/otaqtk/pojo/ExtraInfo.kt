package com.example.otaqtk.pojo

data class ExtraInfo(
    val is_collecting: Boolean,
    var is_favourite: Boolean,
    val followed_chapters: Int,
    val rating : Float,
    val wanted: Boolean,
    val read: Boolean,
    var had : Boolean
    ) {
}
package com.example.otaqtk.pojo
import com.beust.klaxon.*

data class Pagination<T>(
    @Json(name="page")
    val Page: Int,
    @Json(name="per_page")
    val PerPage: Int,
    @Json(name="total_pages")
    val TotalPages: Int,
    @Json(name="has_next")
    val HasNext: Boolean,
    @Json(name="has_prev")
    val HasPrev: Boolean,
    @Json(name="results")
    val Results: ArrayList<T>
) {
}
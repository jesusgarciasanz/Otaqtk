package com.example.otaqtk.api

import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.kitsu_pojo.DataArray
import com.example.otaqtk.kitsu_pojo.DataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {

    //GET TRENDING MANGAS
    @GET("trending/{type}")
    suspend fun getTrendingData(@Path("type") type: String): Response<DataArray>

    //GET MANGAS BY ID
    @GET("{type}/{id}")
    suspend fun getDataByID(@Path("id") id: String, @Path("type") type: String): Response<DataClass>



    /*@GET("categories")
    suspend fun getCategories(): Response<DataArray<CategoryData>>*/
}
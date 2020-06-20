package com.example.otaqtk.api

import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.kitsu_pojo.DataArray
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {

    //GET TRENDING MANGAS
    @GET("trending/manga")
    suspend fun getTrendingManga(): Response<DataArray>

    //GET TRENDING ANIME
    @GET("trending/anime")
    suspend fun getTrendingAnimes(): Response<DataArray>

    //GET MANGAS BY ID
    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: String): Response<Data>


    /*@GET("categories")
    suspend fun getCategories(): Response<DataArray<CategoryData>>*/
}
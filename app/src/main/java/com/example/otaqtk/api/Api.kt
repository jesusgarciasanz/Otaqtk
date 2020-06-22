package com.example.otaqtk.api

import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.kitsu_pojo.DataArray
import com.example.otaqtk.kitsu_pojo.DataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    //GET TRENDING CONTENT
    @GET("trending/{type}")
    suspend fun getTrendingData(@Path("type") type: String): Response<DataArray>

    //GET CONTENT BY ID
    @GET("{type}/{id}")
    suspend fun getDataByID(@Path("id") id: String, @Path("type") type: String): Response<DataClass>

    //GET POPULAR CONTENT
    @GET("{type}?sort=-userCount")
    suspend fun getPopularData(@Path("type") type:String) : Response<DataArray>

    //SEARCH
    @GET("{type}")
    suspend fun searchContent(@Path("type") type:String, @Query("filter[text]") text: String ) : Response<DataArray>

    /*@GET("categories")
    suspend fun getCategories(): Response<DataArray<CategoryData>>*/
}
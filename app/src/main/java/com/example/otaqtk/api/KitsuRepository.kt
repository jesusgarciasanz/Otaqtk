package com.example.otaqtk.api

import com.google.gson.GsonBuilder
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KitsuRepository {
    private val kitsuClient by lazy {
        Retrofit.Builder()
            .baseUrl(Config.KITSU_API)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(
                        LoggingInterceptor.Builder()
                            .addHeader("Accept", "application/vnd.api+json")
                            .addHeader("Content-Type", "application/vnd.api+json")
                            .build()
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Gson
            .build().create(Api::class.java)
    }

    //GET TRENDING CONTENT
    suspend fun getTrendingData(type: String) = kitsuClient.getTrendingData(type)

    //GET CONTENT BY ID
    suspend fun getMangaById(id: String, type: String, include:String) = kitsuClient.getDataByID(id, type, include)

    //GET PUPULAR CONTENT
    suspend fun getPopularData(type: String) = kitsuClient.getPopularData(type)

    //SEARCH CONTENT
    suspend fun searchContent(type: String, text:String) = kitsuClient.searchContent(type, text)

    //GET CATEGORIES
    suspend fun getCategories() = kitsuClient.getCategories()

    //GET CONTENT BY CATEGORY
    suspend fun getByCategory(type: String, filter:String, offset: Int, limit : Int = Config.LIMIT) = kitsuClient.getByCategory(type, filter, offset , limit)

    //GET DATA EPISODES
    suspend fun getDataEpisodes(type: String, id: String, include: String) = kitsuClient.getDataEpisodes(type, id, include)
}
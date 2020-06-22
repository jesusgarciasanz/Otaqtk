package com.example.otaqtk.api

import androidx.databinding.library.BuildConfig
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository {
    private val kitsuClient by lazy {
        Retrofit.Builder()
            .baseUrl(Config.API_URL)
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
    suspend fun getMangaById(id: String, type: String) = kitsuClient.getDataByID(id, type)

    //GET PUPULAR CONTENT
    suspend fun getPopularData(type: String) = kitsuClient.getPopularData(type)

    //SEARCH CONTENT
    suspend fun searchContent(type: String, text:String) = kitsuClient.searchContent(type, text)


    //GET CATEGORIES
   // suspend fun getCategories() = kitsuClient.getCategories()
}
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
                            .addHeader("Content-Type", "application/vnd.api+json")
                            .build()
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Gson
            .build().create(Api::class.java)
    }

    //GET TRENDING MANNGAS
    suspend fun getTrendingManga() = kitsuClient.getTrendingManga()

    //GET TRENDING ANIMES
    suspend fun getTrendingAnimes() = kitsuClient.getTrendingAnimes()

    //GET MANGAS BY ID
    suspend fun getMangaById(id: String) = kitsuClient.getMangaById(id)

    //GET CATEGORIES
   // suspend fun getCategories() = kitsuClient.getCategories()
}
package com.example.otaqtk.api

import com.example.otaqtk.kitsu_pojo.DataClass
import com.example.otaqtk.pojo.ExtraInfo
import com.example.otaqtk.pojo.Profile
import com.example.otaqtk.pojo.UpdatedProfile
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OtaqtkRepository {

    private val otaqtkClient by lazy {
        Retrofit.Builder()
            .baseUrl(Config.OTAQTK_API)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(
                        LoggingInterceptor.Builder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .build()
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Gson
            .build().create(Api::class.java)
    }

    //CREATE PROFILE
    suspend fun createProfile(profile: Profile) = otaqtkClient.createProfile(profile)

    //EDIT PROFILE
    suspend fun editProfile(id: String, username: UpdatedProfile, token:String) = otaqtkClient.editProfile(id, username, token)

    //GET PROFILE
    suspend fun getProfile(id: String, token: String) =otaqtkClient.getProfile(id, token)

    //LOGOUT
    suspend fun logOut (profileId: String, token : String) = otaqtkClient.logOut(profileId, token)

    //LOGIN
    suspend fun loginToken(profileId: String) = otaqtkClient.loginToken(profileId)

    //STATUS
    suspend fun postStatus(type: String, status: String, token: String, dataClass: DataClass) = otaqtkClient.postStatus(type, status, token, dataClass)

    //STATUS LIST
    suspend fun  statusList(type: String, status: String, token: String, dataClass: DataClass) = otaqtkClient.statusList(type, status, token, dataClass)

    //GET ALL CHAPTERS
    suspend fun getAllChapters(type: String, id: String, include:String) = otaqtkClient.getAllChapters(type, id, include)

    //GET MANGA CHAPTERS
    suspend fun getMangaChapters(type: String, id: String, childType: String) = otaqtkClient.getMangaChapters(type, id, childType)

    //GET EXTRA DATA
    suspend fun getExtraData(type: String, profileId: String, id: String, token : String) = otaqtkClient.getExtraData( type, profileId, id, token)

    //UPDATE STATUS
    suspend fun updateStatus( type: String, profileId: String, id: String, token : String, extraInfo: ExtraInfo) = otaqtkClient.updateStatus( type, profileId, id, token, extraInfo)

    //DELETE STATUS
    suspend fun deleteStatus( type: String, profileId: String, id: String, token : String) = otaqtkClient.deleteStatus(type, profileId, id, token)
}
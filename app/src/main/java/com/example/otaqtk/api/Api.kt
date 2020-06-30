package com.example.otaqtk.api

import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.kitsu_pojo.DataArray
import com.example.otaqtk.kitsu_pojo.DataClass
import com.example.otaqtk.pojo.ExtraInfo
import com.example.otaqtk.pojo.Profile
import com.example.otaqtk.pojo.Token
import com.example.otaqtk.pojo.UpdatedProfile
import retrofit2.Response
import retrofit2.http.*


interface Api {

    //GET TRENDING CONTENT
    @GET("trending/{type}")
    suspend fun getTrendingData(@Path("type") type: String): Response<DataArray>

    //GET CONTENT BY ID
    @GET("{type}/{id}")
    suspend fun getDataByID(@Path("id") id: String, @Path("type") type: String, @Query("include") include: String): Response<DataClass>

    //GET POPULAR CONTENT
    @GET("{type}?sort=-userCount")
    suspend fun getPopularData(@Path("type") type:String) : Response<DataArray>

    //SEARCH
    @GET("{type}")
    suspend fun searchContent(@Path("type") type:String, @Query("filter[text]") text: String ) : Response<DataArray>

    //CATEGORIES
    @GET("categories?sort=-totalMediaCount")
    suspend fun getCategories(): Response<DataArray>

    //GET BY CATEGORY
    @GET("{type}")
    suspend fun getByCategory(@Path("type")type: String, @Query("filter[categories]") filter: String, @Query("page[offset]") offset: Int, @Query("page[limit]")limit: Int): Response<DataArray>

    //GET ANIME EPISODES
    @GET("{type}/{id}")
    suspend fun getDataEpisodes(@Path("type") type: String, @Path("id")id:String, @Query("include") include: String): Response<DataClass>

    //CREATE PROFILE
    @POST("profile")
    suspend fun createProfile(@Body profile:Profile) : Response<Token>

    //EDIT PROFILE
    @PATCH("profile/{id}")
    suspend fun editProfile(@Path("id")id: String, @Body username: UpdatedProfile, @Header("User-Token")token:String): Response<Profile>

    //GET PROFILE
    @GET("profile/{id}")
    suspend fun getProfile(@Path("id") id: String,@Header("User-Token")token:String) : Response<Profile>

    //logOut
    @GET("profile/logout")
    suspend fun logOut(@Query("profileId") profileId: String, @Header("User-Token") token : String) : Response<Map<String, Boolean>>

    //LOGIN TOKEN
    @GET("profile/login")
    suspend fun loginToken(@Query("profileId") profileId: String) : Response<Token>

    //STATUS
    @POST("status/{type}")
    suspend fun postStatus(@Path("type") type : String, @Query("status") status: String, @Header("User-Token")token : String, @Body dataClass: DataClass): Response<DataClass>

    //STATUS LIST
    @POST("status/{type}/list")
    suspend fun statusList(@Path("type") type : String, @Query("status") status: String, @Header("User-Token")token : String, @Body dataClass: DataClass): Response<DataClass>

    //GET ALL CHAPTERS
    @GET("{type}/{id}")
    suspend fun getAllChapters(@Path("type") type:String, @Path("id")id:String, @Query("include") include: String) : Response<DataClass>

    //GET MANGA CHAPTERS
    @GET("{type}/{id}/{childType}")
    suspend fun getMangaChapters(@Path("type")type:String, @Path("id") id: String, @Path("childType") childType:String) : Response<DataClass>

    //GET EXTRA DATA
    @GET("status/{type}/{profileId}/{id}")
    suspend fun getExtraData ( @Path("type") type: String, @Path("profileId") profileId: String, @Path("id")id: String, @Header("User-Token") token: String) : Response<ExtraInfo>

    //UPDATE STATUS
    @PATCH("status/{type}/{profileId}/{id}")
    suspend fun updateStatus ( @Path("type") type: String, @Path("profileId") profileId: String, @Path("id")id: String, @Header("User-Token") token: String, @Body extraInfo: ExtraInfo): Response<ExtraInfo>

    //DELETE STATUS
    @DELETE("status/{type}/{profileId}/{id}")
    suspend fun deleteStatus( @Path("type") type: String, @Path("profileId") profileId: String, @Path("id")id: String, @Header("User-Token") token: String) : Response<Map<String,Boolean>>








}
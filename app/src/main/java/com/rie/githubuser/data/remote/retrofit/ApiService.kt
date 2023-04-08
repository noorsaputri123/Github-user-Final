package com.rie.githubuser.data.remote.retrofit

import com.rie.githubuser.BuildConfig
import com.rie.githubuser.data.remote.response.ItemsSearch
import com.rie.githubuser.data.remote.response.ResponseSearch
import com.rie.githubuser.data.remote.response.ResponseSearchDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

//Noor Saputri
interface ApiService {

        @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<ResponseSearch>

        @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): ResponseSearchDetail

        @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<List<ItemsSearch>>

        @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<ItemsSearch>>
}
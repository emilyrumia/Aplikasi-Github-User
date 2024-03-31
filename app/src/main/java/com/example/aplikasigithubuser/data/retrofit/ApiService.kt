package com.example.aplikasigithubuser.data.retrofit

import retrofit2.Call
import retrofit2.http.*
import com.example.aplikasigithubuser.data.response.GithubResponse
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.data.response.DetailUserResponse

interface ApiService {

    @GET("search/users")
    fun getUsers(@Query("q") user: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>
}
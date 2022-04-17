package com.aroman.gitandroid.domain

import com.aroman.gitandroid.domain.entities.GitServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<GitServerResponseData>>
}
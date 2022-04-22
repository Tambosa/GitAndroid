package com.aroman.gitandroid.data.web.github

import com.aroman.gitandroid.domain.entities.GitServerResponseData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Single<List<GitServerResponseData>>
}
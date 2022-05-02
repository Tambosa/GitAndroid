package com.aroman.gitandroid.data.web.github

import com.aroman.gitandroid.domain.usecase.RepositoryUsecase
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitRepoRetrofitImpl : RepositoryUsecase {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitApi::class.java)

    override fun getListRepos(userName: String): Single<List<GitServerResponseData>> {
        return retrofit.listRepos(userName)
    }
}
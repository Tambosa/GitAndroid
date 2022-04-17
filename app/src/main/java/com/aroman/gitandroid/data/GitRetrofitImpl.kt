package com.aroman.gitandroid.data

import com.aroman.gitandroid.domain.GitApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitRetrofitImpl {
    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    fun getGitRetrofitImpl(): GitApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitApi::class.java)
    }
}
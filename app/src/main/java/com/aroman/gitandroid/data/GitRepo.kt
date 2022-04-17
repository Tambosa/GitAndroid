package com.aroman.gitandroid.data

import com.aroman.gitandroid.domain.entities.GitServerResponseData
import io.reactivex.rxjava3.core.Single

interface GitRepo {
    fun getListRepos(userName: String): Single<List<GitServerResponseData>>
}
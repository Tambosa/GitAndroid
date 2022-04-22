package com.aroman.gitandroid.domain

import com.aroman.gitandroid.domain.entities.GitServerResponseData
import io.reactivex.rxjava3.core.Single

interface RepositoryUsecase {
    fun getListRepos(userName: String): Single<List<GitServerResponseData>>
}
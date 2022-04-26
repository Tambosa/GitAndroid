package com.aroman.gitandroid.domain.usecase

import com.aroman.gitandroid.data.web.github.GitServerResponseData
import io.reactivex.rxjava3.core.Single

interface RepositoryUsecase {
    fun getListRepos(userName: String): Single<List<GitServerResponseData>>
}
package com.aroman.gitandroid.data.web.github

import com.aroman.gitandroid.domain.usecase.RepositoryUsecase
import io.reactivex.rxjava3.core.Single

class GitRepoRetrofitImpl(private val gitApi: GitApi) : RepositoryUsecase {
    override fun getListRepos(userName: String): Single<List<GitServerResponseData>> {
        return gitApi.listRepos(userName)
    }
}
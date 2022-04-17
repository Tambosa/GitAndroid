package com.aroman.gitandroid.ui.userDetails

import com.aroman.gitandroid.data.GitRepo
import com.aroman.gitandroid.data.UserLocalRepo
import com.aroman.gitandroid.domain.entities.DbUsers
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.utils.Publisher
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class UserDetailsViewModel(
    private val repository: GitRepo,
    private val userLocalRepo: UserLocalRepo,
) :
    UserDetailsContract.UserDetailsViewModel {

    override val repos: Publisher<List<GitServerResponseData>> = Publisher()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getUser(login: String) {
        //userLocalRepo.clearDb()

        compositeDisposable.add(
            repository.getListRepos(login).subscribeBy {
                repos.post(it)
//                for (repo in repos as List<GitServerResponseData>) {
//                    userLocalRepo.insertUser(
//                        DbUsers(
//                            login = repo.owner.login,
//                            avatarUrl = repo.owner.avatarUrl,
//                            repoName = repo.repoName,
//                            repoLink = repo.repoHtmlUrl
//                        )
//                    )
//                }
            })
    }

    fun unSubscribeDisposable() {
        compositeDisposable.clear()
    }
}

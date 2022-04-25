package com.aroman.gitandroid.ui.userDetails

import android.util.Log
import com.aroman.gitandroid.data.db.room.UserLocalRepo
import com.aroman.gitandroid.data.web.github.GitServerResponseData
import com.aroman.gitandroid.data.web.github.toDbUsers
import com.aroman.gitandroid.domain.RepositoryUsecase
import com.aroman.gitandroid.utils.Publisher
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class UserDetailsViewModel(
    private val repositoryWeb: RepositoryUsecase,
    private val repositoryLocal: RepositoryUsecase,
) : UserDetailsContract.UserDetailsViewModel {

    override val repos: Publisher<List<GitServerResponseData>> = Publisher()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getUser(isOnline: Boolean, login: String) {
        //userLocalRepo.clearDb()
        if (isOnline) {
            Log.d("@@@", "getUser: RETROFIT")
            getRepoListWeb(login)
        } else {
            Log.d("@@@", "getUser: ROOM")
            getRepoListLocal(login)
        }
    }

    private fun getRepoListLocal(login: String) {
        compositeDisposable.add(
            repositoryLocal.getListRepos(login).subscribeBy(
                onSuccess = { data ->
                    repos.post(data)
                },
                onError = {
                    Log.d("@@@", "Error: ${it.message}")
                }
            )
        )
    }

    private fun getRepoListWeb(login: String) {
        compositeDisposable.add(
            repositoryWeb.getListRepos(login).subscribeBy(
                onSuccess = { data ->
                    repos.post(data)
                    for (repo in data) {
                        (repositoryLocal as UserLocalRepo).insertUser(repo.toDbUsers())
                    }
                },
                onError = {
                    Log.d("@@@", "Error: ${it.message}")
                })
        )
    }

    fun unSubscribeDisposable() {
        compositeDisposable.clear()
    }
}

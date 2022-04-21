package com.aroman.gitandroid.ui.userDetails

import android.util.Log
import com.aroman.gitandroid.domain.GitRepo
import com.aroman.gitandroid.data.UserLocalRepo
import com.aroman.gitandroid.domain.entities.*
import com.aroman.gitandroid.utils.BaseViewModel
import com.aroman.gitandroid.utils.Publisher
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class UserDetailsViewModel(
    private val repository: GitRepo,
    private val userLocalRepo: UserLocalRepo,
    override val id: String,
) :
    UserDetailsContract.UserDetailsViewModel, BaseViewModel {

    override val repos: Publisher<List<GitServerResponseData>> = Publisher()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getUser(isOnline: Boolean, login: String) {
        //userLocalRepo.clearDb()
        if (isOnline) {
            Log.d("@@@", "getUser: RETROFIT")
            getUserRepo(login)
        } else {
            Log.d("@@@", "getUser: ROOM")
            getUserDb(login)
        }
    }

    private fun getUserDb(login: String) {
        val localRepoList = userLocalRepo.getAllRepos(login)
        val convertedLocalRepoList = ArrayList<GitServerResponseData>()
        for (dbUser in localRepoList) {
            convertedLocalRepoList.add(dbUser.toGitServerResponseData())
        }
        repos.post(convertedLocalRepoList)
    }

    private fun getUserRepo(login: String) {
        compositeDisposable.add(
            repository.getListRepos(login).subscribeBy(
                onSuccess = { response ->
                    repos.post(response)
                    for (repo in response) {
                        userLocalRepo.insertUser(repo.toDbUsers())
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

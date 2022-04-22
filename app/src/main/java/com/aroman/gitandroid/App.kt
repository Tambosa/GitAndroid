package com.aroman.gitandroid

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.aroman.gitandroid.data.web.github.GitRepoRetrofitImpl
import com.aroman.gitandroid.data.mock.MockUserListRepoImpl
import com.aroman.gitandroid.data.db.room.UserLocalRepo
import com.aroman.gitandroid.domain.RepositoryUsecase
import com.aroman.gitandroid.domain.UsersUsecase
import com.aroman.gitandroid.utils.ViewModelStore

class App : Application() {
    val repoListWeb: RepositoryUsecase by lazy { GitRepoRetrofitImpl() }
    val repoListLocal: RepositoryUsecase by lazy { UserLocalRepo(this) }
    val userListRepo: UsersUsecase by lazy { MockUserListRepoImpl() }
    val viewModelStore by lazy { ViewModelStore() }
}

val Context.app: App
    get() {
        return applicationContext as App
    }

val Fragment.app: App
    get() {
        return requireActivity().app
    }
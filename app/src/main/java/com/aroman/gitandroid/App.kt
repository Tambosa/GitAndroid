package com.aroman.gitandroid

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.aroman.gitandroid.data.GitRepoRetrofitImpl
import com.aroman.gitandroid.data.HardcodedUserListRepoImpl
import com.aroman.gitandroid.domain.GitRepo
import com.aroman.gitandroid.data.UserLocalRepo
import com.aroman.gitandroid.utils.ViewModelStore

class App : Application() {
    val gitRepo: GitRepo by lazy { GitRepoRetrofitImpl() }
    val userLocalRepo by lazy { UserLocalRepo(this) }
    val userListRepo by lazy { HardcodedUserListRepoImpl() }
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
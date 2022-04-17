package com.aroman.gitandroid

import android.app.Application
import android.content.Context
import com.aroman.gitandroid.data.GitRepoRetrofitImpl
import com.aroman.gitandroid.data.GitRepo
import com.aroman.gitandroid.data.UserLocalRepo

class App : Application() {
    val gitRepo: GitRepo by lazy { GitRepoRetrofitImpl() }
    val userLocalRepo by lazy { UserLocalRepo(this) }

}

val Context.app: App
    get() {
        return applicationContext as App
    }
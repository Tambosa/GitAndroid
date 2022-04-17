package com.aroman.gitandroid

import android.app.Application
import android.content.Context
import com.aroman.gitandroid.data.GitRetrofitImpl
import com.aroman.gitandroid.domain.UserLocalRepo

class App : Application() {
    val retrofitImpl by lazy { GitRetrofitImpl() }
    val userLocalRepo by lazy { UserLocalRepo(this) }
}

val Context.app: App
    get() {
        return applicationContext as App
    }
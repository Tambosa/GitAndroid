package com.aroman.gitandroid

import android.app.Application
import android.content.Context
import com.aroman.gitandroid.data.GitRetrofitImpl

class App : Application() {
    val retrofitImpl by lazy { GitRetrofitImpl() }
}

val Context.app: App
    get() {
        return applicationContext as App
    }
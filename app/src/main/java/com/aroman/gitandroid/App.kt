package com.aroman.gitandroid

import android.app.Application
import android.content.Context
import com.aroman.gitandroid.di.AppDependenciesComponent
import com.aroman.gitandroid.di.AppDependenciesModule
import com.aroman.gitandroid.di.DaggerAppDependenciesComponent

class App : Application() {
    lateinit var appDependenciesComponent: AppDependenciesComponent
    override fun onCreate() {
        super.onCreate()
        appDependenciesComponent = DaggerAppDependenciesComponent
            .builder()
            .appDependenciesModule(AppDependenciesModule(this))
            .build()
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }
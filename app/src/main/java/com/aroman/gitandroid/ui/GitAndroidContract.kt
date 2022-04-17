package com.aroman.gitandroid.ui

import androidx.annotation.MainThread
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.utils.Publisher

class GitAndroidContract {
    interface UserDetailsViewModel {
        val repos: Publisher<GitServerResponseData>

        @MainThread
        fun getUser(login: String)
    }
}
package com.aroman.gitandroid.ui.userDetails

import androidx.annotation.MainThread
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.utils.Publisher

class UserDetailsContract {
    interface UserDetailsViewModel {
        val repos: Publisher<List<GitServerResponseData>>

        @MainThread
        fun getUser(login: String)
    }
}
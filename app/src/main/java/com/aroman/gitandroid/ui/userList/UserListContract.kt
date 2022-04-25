package com.aroman.gitandroid.ui.userList

import androidx.annotation.MainThread
import com.aroman.gitandroid.data.mock.UserEntity
import com.aroman.gitandroid.utils.Publisher

class UserListContract {
    interface UserListViewModel {
        val userList: Publisher<List<UserEntity>>

        @MainThread
        fun getUserList()
    }
}
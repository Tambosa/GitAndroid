package com.aroman.gitandroid.ui.userList

import com.aroman.gitandroid.data.mock.UserEntity
import com.aroman.gitandroid.domain.UsersUsecase
import com.aroman.gitandroid.utils.Publisher

class UserListViewModel(private val repository: UsersUsecase) : UserListContract.UserListViewModel {

    override val userList: Publisher<List<UserEntity>> = Publisher()

    override fun getUserList() {
        userList.post(repository.getUsers())
    }
}

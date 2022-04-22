package com.aroman.gitandroid.ui.userList

import com.aroman.gitandroid.domain.UsersUsecase
import com.aroman.gitandroid.domain.entities.UserEntity
import com.aroman.gitandroid.utils.BaseViewModel
import com.aroman.gitandroid.utils.Publisher

class UserListViewModel(
    private val repository: UsersUsecase,
    override val id: String
) : userListContract.UserListViewModel, BaseViewModel {

    override val userList: Publisher<List<UserEntity>> = Publisher()

    override fun getUserList() {
        userList.post(repository.getUsers())
    }
}

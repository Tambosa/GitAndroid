package com.aroman.gitandroid.domain

import com.aroman.gitandroid.data.mock.UserEntity

interface UsersUsecase {
    fun getUsers(): List<UserEntity>
}
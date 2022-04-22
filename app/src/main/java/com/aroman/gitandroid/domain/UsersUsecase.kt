package com.aroman.gitandroid.domain

import com.aroman.gitandroid.domain.entities.UserEntity

interface UsersUsecase {
    fun getUsers(): List<UserEntity>
}
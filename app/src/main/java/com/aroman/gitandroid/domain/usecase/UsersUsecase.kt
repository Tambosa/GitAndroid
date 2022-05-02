package com.aroman.gitandroid.domain.usecase

import com.aroman.gitandroid.domain.entity.UserEntity

interface UsersUsecase {
    fun getUsers(): List<UserEntity>
}
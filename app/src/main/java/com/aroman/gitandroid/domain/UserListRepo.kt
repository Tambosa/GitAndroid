package com.aroman.gitandroid.domain

import com.aroman.gitandroid.domain.entities.UserEntity

interface UserListRepo {
    fun getUsers(): List<UserEntity>
}
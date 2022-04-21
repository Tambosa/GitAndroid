package com.aroman.gitandroid.data

import com.aroman.gitandroid.domain.UserListRepo
import com.aroman.gitandroid.domain.entities.UserEntity
import java.util.*

class HardcodedUserListRepoImpl : UserListRepo {
    override fun getUsers(): List<UserEntity> {
        return listOf(
            UserEntity(generateId(), "tambosa"),
            UserEntity(generateId(), "borhammere"),
            UserEntity(generateId(), "JakeWharton"),
            UserEntity(generateId(), "fabpot"),
        )
    }

    private fun generateId() = UUID.randomUUID().toString()
}
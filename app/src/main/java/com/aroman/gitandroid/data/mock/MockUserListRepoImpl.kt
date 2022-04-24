package com.aroman.gitandroid.data.mock

import com.aroman.gitandroid.domain.UsersUsecase
import java.util.*

class MockUserListRepoImpl : UsersUsecase {
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
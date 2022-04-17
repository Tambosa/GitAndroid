package com.aroman.gitandroid.domain

import android.content.Context
import com.aroman.gitandroid.data.LocalDatabase
import com.aroman.gitandroid.domain.entities.DbUsers

class UserLocalRepo(context: Context) {
    private var db: DbUserDao = LocalDatabase.getInstance(context)?.dbUserDao()!!

    fun getAllUsers(): List<DbUsers> {
        return db.getAllUsers()
    }

    fun getAllRepos(login: String): List<DbUsers> {
        return db.getAllRepos(login)
    }

    fun insertUser(user: DbUsers) {
        db.insertUser(user)
    }

    fun updateUser(user: DbUsers) {
        db.updateUser(user)
    }

    fun deleteUser(user: DbUsers) {
        db.deleteUser(user)
    }

    fun clearDb() {
        db.clearDb()
    }
}
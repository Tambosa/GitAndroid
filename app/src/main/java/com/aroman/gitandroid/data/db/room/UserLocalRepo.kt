package com.aroman.gitandroid.data.db.room

import android.content.Context
import com.aroman.gitandroid.domain.RepositoryUsecase
import com.aroman.gitandroid.domain.entities.DbUsers
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.domain.entities.toGitServerResponseData
import io.reactivex.rxjava3.core.Single

class UserLocalRepo(context: Context) : RepositoryUsecase {
    private var db: DbUserDao = LocalDatabase.getInstance(context)?.dbUserDao()!!

    fun getAllUsers(): List<DbUsers> {
        return db.getAllUsers()
    }

    override fun getListRepos(userName: String): Single<List<GitServerResponseData>> {
        val dbList = db.getAllRepos(userName)
        val srdList = mutableListOf<GitServerResponseData>()
        for (repo in dbList) {
            srdList.add(repo.toGitServerResponseData())
        }
        return Single.just(srdList)
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
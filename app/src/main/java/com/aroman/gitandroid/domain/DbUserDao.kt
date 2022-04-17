package com.aroman.gitandroid.domain

import androidx.room.*
import com.aroman.gitandroid.domain.entities.DbUsers

@Dao
interface DbUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DbUsers)

    @Query ("SELECT * FROM user")
    fun getAllUsers(): List<DbUsers>

    @Query ("SELECT * FROM user WHERE login LIKE :login")
    fun getAllRepos(login: String): List<DbUsers>

    @Update
    fun updateUser(users: DbUsers)

    @Delete
    fun deleteUser(users: DbUsers)

    @Query ("DELETE FROM user")
    fun clearDb()
}
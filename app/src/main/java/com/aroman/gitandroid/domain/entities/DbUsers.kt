package com.aroman.gitandroid.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["repoName"], unique = true)])
data class DbUsers(
    @PrimaryKey(autoGenerate = true) var userId: Int? = null,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatarUrl") var avatarUrl: String,
    @ColumnInfo(name = "repoName") var repoName: String,
    @ColumnInfo(name = "repoLink") var repoLink: String,
)
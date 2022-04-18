package com.aroman.gitandroid.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitServerResponseData(
    @field: SerializedName("name") val repoName: String,
    @field: SerializedName("html_url") val repoHtmlUrl: String,
    @field: SerializedName("owner") val owner: GitServerResponseDataOwner
) : Parcelable

@Parcelize
data class GitServerResponseDataOwner(
    @field: SerializedName("login") val login: String,
    @field: SerializedName("avatar_url") val avatarUrl: String
) : Parcelable

fun GitServerResponseData.toDbUsers() = DbUsers(
    login = owner.login,
    avatarUrl = owner.avatarUrl,
    repoName = repoName,
    repoLink = repoHtmlUrl
)
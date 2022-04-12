package com.aroman.gitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class GitServerResponseData(
    @field: SerializedName("name") val repoName: String,
    @field: SerializedName("html_url") val repoHtmlUrl: String,
    @field: SerializedName("owner") val owner: GitServerResponseDataOwner
)

data class GitServerResponseDataOwner(
    @field: SerializedName("login") val login: String,
    @field: SerializedName("avatar_url") val avatarUrl: String
)
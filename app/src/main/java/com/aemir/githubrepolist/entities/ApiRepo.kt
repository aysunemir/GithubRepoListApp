package com.aemir.githubrepolist.entities

import com.google.gson.annotations.SerializedName

data class ApiRepo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("owner")
    val owner: Owner,
)


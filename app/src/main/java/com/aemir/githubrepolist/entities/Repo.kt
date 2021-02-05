package com.aemir.githubrepolist.entities

data class Repo(
    val repoId: Int,
    val repoName: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val starCount: Int,
    val openIssueCount: Int,
)
package com.aemir.githubrepolist

import com.aemir.githubrepolist.entities.ApiRepo
import com.aemir.githubrepolist.entities.Favorite
import com.aemir.githubrepolist.entities.Owner
import com.aemir.githubrepolist.entities.Repo

object TestModels {

    private val owner = Owner("aysunemir", "https://avatars.githubusercontent.com/u/20208773?v=4")

    val testApiRepo1 = ApiRepo(
        id = 1,
        name = "test repo 1",
        openIssuesCount = 3,
        owner = owner,
        stargazersCount = 5
    )

    val testApiRepo2 = ApiRepo(
        id = 2,
        name = "test repo 2",
        openIssuesCount = 2,
        owner = owner,
        stargazersCount = 6
    )

    val testRepo1 = Repo(
        repoId = 1,
        repoName = "test repo 1",
        ownerName = "aysunemir",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/20208773?v=4",
        openIssueCount = 3,
        starCount = 5,
        favorite = true
    )

    val testRepo2 = Repo(
        repoId = 2,
        repoName = "test repo 2",
        ownerName = "aysunemir",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/20208773?v=4",
        openIssueCount = 2,
        starCount = 6,
        favorite = false
    )

    val testFavorite1 = Favorite(1, "aysunemir")

}
package com.aemir.githubrepolist.mappers

import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.entities.ApiRepo

class RepoMapper : EntityMapper<ApiRepo, Repo> {

    override fun mapFrom(entity: ApiRepo): Repo =
        Repo(
            repoId = entity.id,
            repoName = entity.name,
            ownerName = entity.owner.login,
            ownerAvatarUrl = entity.owner.avatarUrl,
            starCount = entity.stargazersCount,
            openIssueCount = entity.openIssuesCount
        )
}
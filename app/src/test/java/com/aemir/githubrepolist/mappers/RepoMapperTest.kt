package com.aemir.githubrepolist.mappers

import com.aemir.githubrepolist.entities.ApiRepo
import com.aemir.githubrepolist.entities.Owner
import com.aemir.githubrepolist.entities.Repo
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RepoMapperTest {

    private val mapper = RepoMapper()

    @Test
    fun `test map from apiRepo to repo`() {
        val repo = mapper.mapFrom(testApiRepo)
        assertThat(repo).isEqualTo(testRepo)
    }
}

val testApiRepo = ApiRepo(
    id = 1,
    name = "test repo",
    openIssuesCount = 3,
    owner = Owner("aysunemir", "https://avatars.githubusercontent.com/u/20208773?v=4"),
    stargazersCount = 5
)

val testRepo = Repo(
    repoId = 1,
    repoName = "test repo",
    ownerName = "aysunemir",
    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/20208773?v=4",
    openIssueCount = 3,
    starCount = 5
)
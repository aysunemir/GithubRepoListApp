package com.aemir.githubrepolist.domain

import com.aemir.githubrepolist.api.Resource
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.repositories.GithubRepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GithubRepoRepository
) {

    fun getUserRepos(username: String): Flow<Resource<List<Repo>>> =
        repository.getUserRepos(username)

}
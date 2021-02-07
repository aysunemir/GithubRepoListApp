package com.aemir.githubrepolist.repositories

import com.aemir.githubrepolist.api.Resource
import com.aemir.githubrepolist.entities.Repo
import kotlinx.coroutines.flow.Flow

interface GithubRepoRepository {
    fun getUserRepos(username: String): Flow<Resource<List<Repo>>>
}
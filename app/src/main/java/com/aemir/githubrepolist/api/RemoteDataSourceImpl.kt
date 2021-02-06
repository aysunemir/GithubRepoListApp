package com.aemir.githubrepolist.api

import com.aemir.githubrepolist.entities.ApiRepo
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : RemoteDataSource {

    override suspend fun getUserRepos(username: String): Response<List<ApiRepo>> =
        githubService.getUserRepos(username)

}
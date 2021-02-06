package com.aemir.githubrepolist.api

import com.aemir.githubrepolist.entities.ApiRepo
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getUserRepos(username: String): Response<List<ApiRepo>>
}
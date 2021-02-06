package com.aemir.githubrepolist.api

import com.aemir.githubrepolist.entities.ApiRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): Response<List<ApiRepo>>

}
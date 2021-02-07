package com.aemir.githubrepolist.repositories

import com.aemir.githubrepolist.api.Error
import com.aemir.githubrepolist.api.Loading
import com.aemir.githubrepolist.api.RemoteDataSource
import com.aemir.githubrepolist.api.Resource
import com.aemir.githubrepolist.api.Success
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.mappers.RepoMapper
import com.aemir.githubrepolist.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: RepoMapper
) : GithubRepoRepository {

    override fun getUserRepos(username: String): Flow<Resource<List<Repo>>> = flow {
        emit(Loading)
        val response = remoteDataSource.getUserRepos(username)
        if (response.isSuccessful) {
            response.body()?.let { apiRepoList ->
                val repoList = apiRepoList.map {
                    mapper.mapFrom(it)
                }
                emit(Success(repoList))
            } ?: emit(Error(Constants.NO_REPO_FOUND))
        } else {
            emit(Error(Constants.NO_REPO_FOUND))
        }
    }

}
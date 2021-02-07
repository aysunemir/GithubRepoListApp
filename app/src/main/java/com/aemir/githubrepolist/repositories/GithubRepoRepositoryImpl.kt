package com.aemir.githubrepolist.repositories

import com.aemir.githubrepolist.api.*
import com.aemir.githubrepolist.db.LocalDataSource
import com.aemir.githubrepolist.entities.Favorite
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.mappers.RepoMapper
import com.aemir.githubrepolist.util.Constants
import com.aemir.githubrepolist.util.Constants.NETWORK_ERROR
import com.aemir.githubrepolist.util.NetworkUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val mapper: RepoMapper,
    private val networkUtil: NetworkUtil
) : GithubRepoRepository {

    override fun getUserRepos(username: String): Flow<Resource<List<Repo>>> = flow {
        if (networkUtil.isConnected()) {
            emit(Loading)
            val response = remoteDataSource.getUserRepos(username)
            if (response.isSuccessful) {
                response.body()?.let { apiRepoList ->
                    localDataSource.getFavoriteIds(username).collect { idList ->
                        val repoList = apiRepoList.map { apiRepo ->
                            mapper.mapFrom(apiRepo).also { repo ->
                                repo.favorite = idList.contains(repo.repoId)
                            }
                        }
                        emit(Success(repoList))
                    }
                } ?: emit(Success(null))
            } else {
                emit(Error(Constants.SERVICE_ERROR))
            }
        } else {
            emit(Error(NETWORK_ERROR))
        }
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        localDataSource.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        localDataSource.deleteFavorite(favorite)
    }
}
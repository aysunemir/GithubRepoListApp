package com.aemir.githubrepolist.domain

import com.aemir.githubrepolist.entities.Favorite
import com.aemir.githubrepolist.repositories.GithubRepoRepository
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(
    private val repository: GithubRepoRepository
) {

    suspend fun insertFavorite(favorite: Favorite) {
        repository.insertFavorite(favorite)
    }

}
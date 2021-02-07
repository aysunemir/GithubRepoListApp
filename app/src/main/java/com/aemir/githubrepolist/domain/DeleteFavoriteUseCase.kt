package com.aemir.githubrepolist.domain

import com.aemir.githubrepolist.entities.Favorite
import com.aemir.githubrepolist.repositories.GithubRepoRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: GithubRepoRepository
) {

    suspend fun deleteFavorite(favorite: Favorite) {
        repository.deleteFavorite(favorite)
    }

}
package com.aemir.githubrepolist.db

import com.aemir.githubrepolist.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    fun getFavoriteIds(username: String): Flow<List<Int>>
}
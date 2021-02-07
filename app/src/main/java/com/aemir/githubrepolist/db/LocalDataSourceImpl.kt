package com.aemir.githubrepolist.db

import com.aemir.githubrepolist.entities.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : LocalDataSource {

    override suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }

    override fun getFavoriteIds(username: String): Flow<List<Int>> =
        favoriteDao.getFavoriteIdsByUser(username)

}
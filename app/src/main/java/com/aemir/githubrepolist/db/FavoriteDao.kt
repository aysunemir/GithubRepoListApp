package com.aemir.githubrepolist.db

import androidx.room.*
import com.aemir.githubrepolist.entities.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT repoId from favorite_db WHERE ownerName = :username")
    fun getFavoriteIdsByUser(username: String): Flow<List<Int>>

}
package com.aemir.githubrepolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aemir.githubrepolist.entities.Favorite

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
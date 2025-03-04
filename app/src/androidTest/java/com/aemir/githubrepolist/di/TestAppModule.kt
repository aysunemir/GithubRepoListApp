package com.aemir.githubrepolist.di

import android.content.Context
import androidx.room.Room
import com.aemir.githubrepolist.db.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("favorite_db")
    fun provideFavoriteDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            FavoriteDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

}
package com.aemir.githubrepolist.di

import android.content.Context
import androidx.room.Room
import com.aemir.githubrepolist.BuildConfig
import com.aemir.githubrepolist.api.GithubService
import com.aemir.githubrepolist.api.RemoteDataSource
import com.aemir.githubrepolist.api.RemoteDataSourceImpl
import com.aemir.githubrepolist.db.FavoriteDatabase
import com.aemir.githubrepolist.mappers.RepoMapper
import com.aemir.githubrepolist.repositories.GithubRepoRepository
import com.aemir.githubrepolist.repositories.GithubRepoRepositoryImpl
import com.aemir.githubrepolist.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(logger)
        }
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): GithubService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
        .create(GithubService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: GithubService): RemoteDataSource =
        RemoteDataSourceImpl(service)

    @Singleton
    @Provides
    fun provideGithubRepoRepository(
        remoteDataSource: RemoteDataSource,
        mapper: RepoMapper
    ): GithubRepoRepository =
        GithubRepoRepositoryImpl(
            remoteDataSource,
            mapper
        )

    @Singleton
    @Provides
    fun provideFavoriteDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FavoriteDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideFavoriteDao(
        database: FavoriteDatabase
    ) = database.favoriteDao()
}
package com.aemir.githubrepolist.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.aemir.githubrepolist.entities.Favorite
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class FavoriteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("favorite_db")
    lateinit var favoriteDatabase: FavoriteDatabase

    lateinit var favoriteDao: FavoriteDao

    @Before
    fun setup() {
        hiltRule.inject()
        favoriteDao = favoriteDatabase.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        favoriteDatabase.close()
    }

    @Test
    fun test_insertFavorite() = runBlockingTest {
        favoriteDao.insertFavorite(testFavorite1)
        val favoriteIdList = favoriteDao.getFavoriteIdsByUser("aysunemir").first()
        assertThat(favoriteIdList).contains(testFavorite1.repoId)
    }

    @Test
    fun test_deleteFavorite() = runBlockingTest {
        favoriteDao.insertFavorite(testFavorite1)
        favoriteDao.insertFavorite(testFavorite2)
        favoriteDao.deleteFavorite(testFavorite1)
        val favoriteIdList = favoriteDao.getFavoriteIdsByUser("aysunemir").first()
        assertThat(favoriteIdList).doesNotContain(testFavorite1.repoId)
        assertThat(favoriteIdList).hasSize(1)
    }

    @Test
    fun test_getFavoriteIdsByUser() = runBlockingTest {
        favoriteDao.insertFavorite(testFavorite1)
        favoriteDao.insertFavorite(testFavorite2)
        favoriteDao.insertFavorite(testFavorite3)
        val favoriteIdList = favoriteDao.getFavoriteIdsByUser("aysunemir").first()
        assertThat(favoriteIdList).hasSize(2)
    }

}

val testFavorite1 = Favorite(1, "aysunemir")

val testFavorite2 = Favorite(2, "aysunemir")

val testFavorite3 = Favorite(3, "emir")
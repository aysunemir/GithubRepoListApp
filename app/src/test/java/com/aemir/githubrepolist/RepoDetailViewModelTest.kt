package com.aemir.githubrepolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aemir.githubrepolist.TestModels.testFavorite1
import com.aemir.githubrepolist.TestModels.testFavorite2
import com.aemir.githubrepolist.TestModels.testRepo1
import com.aemir.githubrepolist.TestModels.testRepo2
import com.aemir.githubrepolist.domain.DeleteFavoriteUseCase
import com.aemir.githubrepolist.domain.InsertFavoriteUseCase
import com.aemir.githubrepolist.mappers.FavoriteMapper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepoDetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var insertFavoriteUseCase: InsertFavoriteUseCase

    @Mock
    private lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    @Mock
    private lateinit var favoriteMapper: FavoriteMapper

    private lateinit var viewModel: RepoDetailViewModel

    @Before
    fun setUp() {
        viewModel = RepoDetailViewModel(
            deleteFavoriteUseCase,
            insertFavoriteUseCase,
            favoriteMapper
        )
    }

    @Test
    fun test_deleteFavorite() = coroutineRule.runBlockingTest {
        viewModel.setRepo(testRepo1)
        `when`(favoriteMapper.mapFrom(testRepo1)).thenReturn(testFavorite1)
        viewModel.deleteFavorite()

        Mockito.verify(deleteFavoriteUseCase).deleteFavorite(testFavorite1)
        assertThat(viewModel.repo.getOrAwaitValueTest().favorite).isFalse()
    }

    @Test
    fun test_insertFavorite() = coroutineRule.runBlockingTest {
        viewModel.setRepo(testRepo2)
        `when`(favoriteMapper.mapFrom(testRepo2)).thenReturn(testFavorite2)
        viewModel.insertFavorite()

        Mockito.verify(insertFavoriteUseCase).insertFavorite(testFavorite2)
        assertThat(viewModel.repo.getOrAwaitValueTest().favorite).isTrue()
    }

    @Test
    fun test_getMenuIcon_selected() {
        val iconId = viewModel.getMenuIcon(true)
        assertThat(iconId).isEqualTo(R.drawable.ic_star_selected)
    }

    @Test
    fun test_getMenuIcon_unselected() {
        val iconId = viewModel.getMenuIcon(false)
        assertThat(iconId).isEqualTo(R.drawable.ic_star_unselected)
    }
}
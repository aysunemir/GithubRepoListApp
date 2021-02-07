package com.aemir.githubrepolist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aemir.githubrepolist.MainCoroutineRule
import com.aemir.githubrepolist.TestModels.testRepo1
import com.aemir.githubrepolist.TestModels.testRepo2
import com.aemir.githubrepolist.api.Error
import com.aemir.githubrepolist.api.Loading
import com.aemir.githubrepolist.api.Success
import com.aemir.githubrepolist.domain.GetUserReposUseCase
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.getOrAwaitValueTest
import com.aemir.githubrepolist.util.Constants.SERVICE_ERROR
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepoListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getUserReposUseCase: GetUserReposUseCase

    private lateinit var viewModel: RepoListViewModel

    @Before
    fun setup() {
        viewModel = RepoListViewModel(getUserReposUseCase)
    }

    @Test
    fun test_loading() = coroutineRule.runBlockingTest {
        val flow = flow {
            emit(Loading)
        }
        `when`(getUserReposUseCase.getUserRepos("aysunemir")).thenReturn(flow)

        viewModel.getRepos("aysunemir")
        val viewState = viewModel.viewState.getOrAwaitValueTest()

        assertThat(viewState.isLoading()).isTrue()
        assertThat(viewState.isEmpty()).isFalse()
    }

    @Test
    fun test_success() = coroutineRule.runBlockingTest {
        val expected = listOf(testRepo1, testRepo2)
        val flow = flow {
            emit(Success(expected))
        }
        `when`(getUserReposUseCase.getUserRepos("aysunemir")).thenReturn(flow)

        viewModel.getRepos("aysunemir")
        val repoList = viewModel.repoList.getOrAwaitValueTest()
        val viewState = viewModel.viewState.getOrAwaitValueTest()

        assertThat(repoList).isEqualTo(expected)
        assertThat(viewState.isLoading()).isFalse()
        assertThat(viewState.isEmpty()).isFalse()
    }

    @Test
    fun test_success_emptyList() = coroutineRule.runBlockingTest {
        val expected = emptyList<Repo>()
        val flow = flow {
            emit(Success(expected))
        }
        `when`(getUserReposUseCase.getUserRepos("aysunemir")).thenReturn(flow)

        viewModel.getRepos("aysunemir")
        val repoList = viewModel.repoList.getOrAwaitValueTest()
        val viewState = viewModel.viewState.getOrAwaitValueTest()

        assertThat(repoList).isEqualTo(expected)
        assertThat(viewState.isLoading()).isFalse()
        assertThat(viewState.isEmpty()).isTrue()
    }

    @Test
    fun test_error() = coroutineRule.runBlockingTest {
        val flow = flow {
            emit(Error(SERVICE_ERROR))
        }
        `when`(getUserReposUseCase.getUserRepos("aysunemir")).thenReturn(flow)

        viewModel.getRepos("aysunemir")
        val repoList = viewModel.repoList.getOrAwaitValueTest()
        val viewState = viewModel.viewState.getOrAwaitValueTest()
        val error = viewModel.error.getOrAwaitValueTest().getContentIfNotHandled()

        assertThat(repoList).isEqualTo(emptyList<Repo>())
        assertThat(viewState.isLoading()).isFalse()
        assertThat(viewState.isEmpty()).isFalse()
        assertThat(error).isEqualTo(SERVICE_ERROR)
    }

}
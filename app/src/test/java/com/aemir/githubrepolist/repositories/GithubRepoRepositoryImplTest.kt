package com.aemir.githubrepolist.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aemir.githubrepolist.MainCoroutineRule
import com.aemir.githubrepolist.TestModels.testApiRepo1
import com.aemir.githubrepolist.TestModels.testApiRepo2
import com.aemir.githubrepolist.TestModels.testFavorite1
import com.aemir.githubrepolist.TestModels.testRepo1
import com.aemir.githubrepolist.TestModels.testRepo2
import com.aemir.githubrepolist.api.Error
import com.aemir.githubrepolist.api.RemoteDataSourceImpl
import com.aemir.githubrepolist.api.Success
import com.aemir.githubrepolist.db.LocalDataSourceImpl
import com.aemir.githubrepolist.entities.ApiRepo
import com.aemir.githubrepolist.mappers.RepoMapper
import com.aemir.githubrepolist.util.Constants.NETWORK_ERROR
import com.aemir.githubrepolist.util.Constants.SERVICE_ERROR
import com.aemir.githubrepolist.util.NetworkUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GithubRepoRepositoryImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mapper: RepoMapper

    @Mock
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Mock
    private lateinit var localDataSource: LocalDataSourceImpl

    @Mock
    private lateinit var networkUtil: NetworkUtil

    private lateinit var repository: GithubRepoRepository

    @Before
    fun setup() {
        repository = GithubRepoRepositoryImpl(
            remoteDataSource,
            localDataSource,
            mapper,
            networkUtil
        )
    }

    @Test
    fun test_successResponse_nonEmptyList() = coroutineRule.runBlockingTest {
        val expectedResponse = Response.success(listOf(testApiRepo1, testApiRepo2))
        val expectedFavoriteIds = flow { emit(listOf(1)) }

        `when`(networkUtil.isConnected()).thenReturn(true)

        `when`(remoteDataSource.getUserRepos("aysunemir")).thenReturn(expectedResponse)
        `when`(localDataSource.getFavoriteIds("aysunemir")).thenReturn(expectedFavoriteIds)

        `when`(mapper.mapFrom(testApiRepo1)).thenReturn(testRepo1)
        `when`(mapper.mapFrom(testApiRepo2)).thenReturn(testRepo2)

        val result = repository.getUserRepos("aysunemir").drop(1).first() as Success
        val expectedData = listOf(testRepo1, testRepo2)
        assertThat(result).isEqualTo(Success(expectedData))
    }

    @Test
    fun test_successResponse_nullOrEmptyList() = coroutineRule.runBlockingTest {
        val expectedResponse = Response.success<List<ApiRepo>>(200, null)

        `when`(networkUtil.isConnected()).thenReturn(true)
        `when`(remoteDataSource.getUserRepos("aysunemir")).thenReturn(expectedResponse)

        val result = repository.getUserRepos("aysunemir").drop(1).first() as Success
        assertThat(result).isEqualTo(Success(null))
    }

    @Test
    fun test_errorResponse() = coroutineRule.runBlockingTest {
        val expectedResponse = Response.error<List<ApiRepo>>(400, "".toResponseBody())

        `when`(networkUtil.isConnected()).thenReturn(true)
        `when`(remoteDataSource.getUserRepos("aysunemir")).thenReturn(expectedResponse)

        val result = repository.getUserRepos("aysunemir").drop(1).first() as Error
        val expectedMessage = SERVICE_ERROR
        assertThat(result).isEqualTo(Error(expectedMessage))
    }

    @Test
    fun test_noNetwork() = coroutineRule.runBlockingTest {
        `when`(networkUtil.isConnected()).thenReturn(false)

        val result = repository.getUserRepos("aysunemir").first() as Error
        val expectedMessage = NETWORK_ERROR
        assertThat(result).isEqualTo(Error(expectedMessage))
    }

    @Test
    fun test_insertFavorite() = coroutineRule.runBlockingTest {
        repository.insertFavorite(testFavorite1)
        Mockito.verify(localDataSource).insertFavorite(testFavorite1)
    }

    @Test
    fun test_deleteFavorite() = coroutineRule.runBlockingTest {
        repository.deleteFavorite(testFavorite1)
        Mockito.verify(localDataSource).deleteFavorite(testFavorite1)
    }

}


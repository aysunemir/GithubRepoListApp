package com.aemir.githubrepolist.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aemir.githubrepolist.MainCoroutineRule
import com.aemir.githubrepolist.TestModels.testApiRepo1
import com.aemir.githubrepolist.TestModels.testApiRepo2
import com.aemir.githubrepolist.TestModels.testRepo1
import com.aemir.githubrepolist.TestModels.testRepo2
import com.aemir.githubrepolist.api.Error
import com.aemir.githubrepolist.api.RemoteDataSourceImpl
import com.aemir.githubrepolist.api.Success
import com.aemir.githubrepolist.entities.ApiRepo
import com.aemir.githubrepolist.mappers.RepoMapper
import com.aemir.githubrepolist.util.Constants.SERVICE_ERROR
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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

    private lateinit var repository: GithubRepoRepository

    @Before
    fun setup() {
        repository = GithubRepoRepositoryImpl(remoteDataSource, mapper)
    }

    @Test
    fun test_successResponse() =
        coroutineRule.runBlockingTest {
            val expectedResponse = Response.success(listOf(testApiRepo1, testApiRepo2))

            `when`(remoteDataSource.getUserRepos("aysunemir")).thenReturn(expectedResponse)
            `when`(mapper.mapFrom(testApiRepo1)).thenReturn(testRepo1)
            `when`(mapper.mapFrom(testApiRepo2)).thenReturn(testRepo2)

            val result = repository.getUserRepos("aysunemir").drop(1).first() as Success
            val expectedData = listOf(testRepo1, testRepo2)

            assertThat(result).isEqualTo(Success(expectedData))
        }

    @Test
    fun test_emptyResponse() =
        coroutineRule.runBlockingTest {
            val expectedResponse = Response.success<List<ApiRepo>>(200, null)

            `when`(remoteDataSource.getUserRepos("aysunemir")).thenReturn(expectedResponse)

            val result = repository.getUserRepos("aysunemir").drop(1).first() as Success

            assertThat(result).isEqualTo(Success(null))
        }

    @Test
    fun test_errorResponse() =
        coroutineRule.runBlockingTest {
            val expectedResponse = Response.error<List<ApiRepo>>(400, "".toResponseBody())

            `when`(remoteDataSource.getUserRepos("aysunemir")).thenReturn(expectedResponse)

            val result = repository.getUserRepos("aysunemir").drop(1).first() as Error
            val expectedMessage = SERVICE_ERROR

            assertThat(result).isEqualTo(Error(expectedMessage))
        }

}


package com.aemir.githubrepolist.mappers

import com.aemir.githubrepolist.TestModels.testApiRepo1
import com.aemir.githubrepolist.TestModels.testRepo1
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RepoMapperTest {

    private val mapper = RepoMapper()

    @Test
    fun `test map from apiRepo to repo`() {
        val repo = mapper.mapFrom(testApiRepo1)
        assertThat(repo).isEqualTo(testRepo1)
    }
}
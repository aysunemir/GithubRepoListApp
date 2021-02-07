package com.aemir.githubrepolist.mappers

import com.aemir.githubrepolist.TestModels.testFavorite1
import com.aemir.githubrepolist.TestModels.testRepo1
import com.google.common.truth.Truth
import org.junit.Test

class FavoriteMapperTest {

    private val mapper = FavoriteMapper()

    @Test
    fun `test map from repo to favorite`() {
        val favorite = mapper.mapFrom(testRepo1)
        Truth.assertThat(favorite).isEqualTo(testFavorite1)
    }

}
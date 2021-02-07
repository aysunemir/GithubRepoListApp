package com.aemir.githubrepolist.ui

import com.aemir.githubrepolist.api.Loading
import com.aemir.githubrepolist.api.Resource
import com.aemir.githubrepolist.api.Success
import com.aemir.githubrepolist.entities.Repo

data class RepoListViewState(
    val resource: Resource<List<Repo>>,
) {

    fun isLoading() = resource is Loading

    fun isEmpty() = resource is Success && resource.data.isNullOrEmpty()

}
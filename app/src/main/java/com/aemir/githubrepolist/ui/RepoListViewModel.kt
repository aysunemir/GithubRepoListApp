package com.aemir.githubrepolist.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aemir.githubrepolist.api.Error
import com.aemir.githubrepolist.api.Resource
import com.aemir.githubrepolist.api.Success
import com.aemir.githubrepolist.domain.GetUserReposUseCase
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.util.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RepoListViewModel @ViewModelInject constructor(
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<RepoListViewState>()
    val viewState: LiveData<RepoListViewState> = _viewState

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _repoList = MutableLiveData<MutableList<Repo>>()
    val repoList: LiveData<MutableList<Repo>> = _repoList

    fun getRepos(username: String) = viewModelScope.launch {
        getUserReposUseCase.getUserRepos(username).collect { resource ->
            handleResponse(resource)
        }
    }

    private fun handleResponse(resource: Resource<List<Repo>>) {
        setViewState(resource)
        if (resource is Success) {
            setRepoList(resource.data)
        } else if (resource is Error) {
            setRepoList(null)
            setError(resource.message)
        }
    }

    private fun setViewState(resource: Resource<List<Repo>>) {
        _viewState.value = RepoListViewState(resource)
    }


    private fun setRepoList(data: List<Repo>?) {
        data?.let {
            _repoList.value = it.toMutableList()
        } ?: run {
            _repoList.value = mutableListOf()
        }
    }

    private fun setError(message: String) {
        _error.value = Event(message)
    }

}
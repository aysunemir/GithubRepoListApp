package com.aemir.githubrepolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aemir.githubrepolist.entities.Repo

class RepoDetailViewModel : ViewModel() {

    private val _repo = MutableLiveData<Repo>()
    val repo: LiveData<Repo> = _repo

    fun setRepo(repo: Repo) {
        _repo.value = repo
    }

}
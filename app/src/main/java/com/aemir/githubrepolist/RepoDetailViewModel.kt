package com.aemir.githubrepolist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aemir.githubrepolist.domain.DeleteFavoriteUseCase
import com.aemir.githubrepolist.domain.InsertFavoriteUseCase
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.mappers.FavoriteMapper
import kotlinx.coroutines.launch

class RepoDetailViewModel @ViewModelInject constructor(
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val favoriteMapper: FavoriteMapper
) : ViewModel() {

    private val _repo = MutableLiveData<Repo>()
    val repo: LiveData<Repo> = _repo

    fun setRepo(repo: Repo) {
        _repo.value = repo
    }

    fun insertFavorite() = viewModelScope.launch {
        repo.value?.let {
            val favorite = favoriteMapper.mapFrom(it)
            insertFavoriteUseCase.insertFavorite(favorite)
            it.favorite = true
        }
    }

    fun deleteFavorite() = viewModelScope.launch {
        repo.value?.let {
            val favorite = favoriteMapper.mapFrom(it)
            deleteFavoriteUseCase.deleteFavorite(favorite)
            it.favorite = false
        }
    }

    fun getMenuIcon(selected: Boolean): Int {
        return if (selected) {
            R.drawable.ic_star_selected
        } else {
            R.drawable.ic_star_unselected
        }
    }

}
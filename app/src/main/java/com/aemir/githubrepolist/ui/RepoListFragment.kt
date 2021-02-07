package com.aemir.githubrepolist.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aemir.githubrepolist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.repo_list_fragment) {

    private val viewModel: RepoListViewModel by viewModels()

}
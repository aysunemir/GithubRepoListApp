package com.aemir.githubrepolist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.aemir.githubrepolist.R
import com.aemir.githubrepolist.databinding.RepoListFragmentBinding
import com.aemir.githubrepolist.entities.Repo
import com.aemir.githubrepolist.util.hideKeyboard
import com.aemir.githubrepolist.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.repo_list_fragment), RepoClickListener {

    private lateinit var binding: RepoListFragmentBinding
    private val viewModel: RepoListViewModel by viewModels()

    @Inject
    lateinit var repoAdapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RepoListFragmentBinding.bind(view).apply {
            recyclerView.apply {
                repoAdapter.setClickListener(this@RepoListFragment)
                adapter = repoAdapter
                val itemDecoration = DividerItemDecoration(this.context, VERTICAL)
                addItemDecoration(itemDecoration)
            }

            bSubmit.setOnClickListener {
                it.hideKeyboard()
                searchEditText.clearFocus()
                viewModel.getRepos(searchEditText.text.toString())
            }
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            binding.viewState = it
        })
        viewModel.repoList.observe(viewLifecycleOwner, {
            repoAdapter.submitList(it)
        })
        viewModel.error.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { message ->
                binding.root.showSnackBar(message)
            }
        })
    }

    override fun onClickRepo(repo: Repo) {
        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(
            repo,
            repo.repoName
        )
        findNavController().navigate(action)
    }

}
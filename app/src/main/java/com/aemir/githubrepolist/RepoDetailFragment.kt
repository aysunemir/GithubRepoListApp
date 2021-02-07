package com.aemir.githubrepolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aemir.githubrepolist.databinding.RepoDetailFragmentBinding

class RepoDetailFragment : Fragment(R.layout.repo_detail_fragment) {

    private lateinit var binding: RepoDetailFragmentBinding
    private val viewModel: RepoDetailViewModel by viewModels()
    private val args: RepoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RepoDetailFragmentBinding.bind(view)
        viewModel.setRepo(args.repo)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.repo.observe(viewLifecycleOwner, {
            binding.repo = it
        })
    }

}
package com.aemir.githubrepolist

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aemir.githubrepolist.databinding.RepoDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoDetailFragment : Fragment(R.layout.repo_detail_fragment) {

    private lateinit var binding: RepoDetailFragmentBinding
    private val viewModel: RepoDetailViewModel by viewModels()
    private val args: RepoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.repo_detail, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.getItem(0).apply {
            isChecked = viewModel.repo.value!!.favorite
            icon = ContextCompat.getDrawable(
                requireContext(),
                viewModel.getMenuIcon(isChecked)
            )
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                if (item.isChecked) {
                    viewModel.deleteFavorite()
                } else {
                    viewModel.insertFavorite()
                }
                item.apply {
                    isChecked = !item.isChecked
                    icon = ContextCompat.getDrawable(
                        requireContext(),
                        viewModel.getMenuIcon(isChecked)
                    )
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
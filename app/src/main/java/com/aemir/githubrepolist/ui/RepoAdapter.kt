package com.aemir.githubrepolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aemir.githubrepolist.databinding.ItemRepoBinding
import com.aemir.githubrepolist.entities.Repo
import javax.inject.Inject

class RepoAdapter @Inject constructor() :
    ListAdapter<Repo, RepoAdapter.RepoViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(layoutInflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bind(repo)
    }

    class RepoViewHolder constructor(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.apply {
                this.repoName = repo.repoName
                executePendingBindings()
            }
        }
    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem.repoId == newItem.repoId
}

package com.aemir.githubrepolist.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    val repoId: Int,
    val repoName: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val starCount: Int,
    val openIssueCount: Int,
    var favorite: Boolean = false
) : Parcelable
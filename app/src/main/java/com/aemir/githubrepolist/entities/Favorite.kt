package com.aemir.githubrepolist.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aemir.githubrepolist.util.Constants

@Entity(tableName = Constants.DATABASE_NAME)
data class Favorite(
    @PrimaryKey
    val repoId: Int,
    val ownerName: String
)
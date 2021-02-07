package com.aemir.githubrepolist.mappers

import com.aemir.githubrepolist.entities.Favorite
import com.aemir.githubrepolist.entities.Repo
import javax.inject.Inject

class FavoriteMapper @Inject constructor() : EntityMapper<Repo, Favorite> {

    override fun mapFrom(entity: Repo): Favorite = Favorite(entity.repoId, entity.ownerName)

}
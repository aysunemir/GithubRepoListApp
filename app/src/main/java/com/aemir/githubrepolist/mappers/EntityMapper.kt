package com.aemir.githubrepolist.mappers

interface EntityMapper<InputEntity, OutputEntity> {

    fun mapFrom(entity: InputEntity): OutputEntity

}
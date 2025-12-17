package com.example.spaceexpo.di

import com.example.spaceexpo.data.repository.SpaceRepositoryImpl
import com.example.spaceexpo.domain.SpaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSpaceRepository(
        spaceRepositoryImpl: SpaceRepositoryImpl
    ): SpaceRepository
}
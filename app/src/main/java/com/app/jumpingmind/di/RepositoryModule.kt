package com.app.jumpingmind.di

import com.app.jumpingmind.data.repository.BeersRepositoryImpl
import com.app.jumpingmind.domain.repository.BeersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindConverterRepository(beersRepositoryImpl: BeersRepositoryImpl): BeersRepository
}
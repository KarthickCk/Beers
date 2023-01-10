package com.app.jumpingmind.di

import android.content.Context
import androidx.room.Room
import com.app.jumpingmind.data.room.BeersDAO
import com.app.jumpingmind.data.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): Database {
        return Room.databaseBuilder(
            applicationContext,
            Database::class.java, "ratesDB"
        ).build()
    }

    @Provides
    fun providesRateDAO(database: Database): BeersDAO {
        return database.beerDao()
    }
}
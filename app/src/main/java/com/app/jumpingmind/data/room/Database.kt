package com.app.jumpingmind.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BeerInfo::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun beerDao(): BeersDAO
}
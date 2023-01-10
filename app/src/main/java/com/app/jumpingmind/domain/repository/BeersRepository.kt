package com.app.jumpingmind.domain.repository

import androidx.paging.PagingData
import com.app.jumpingmind.data.room.BeerInfo
import com.app.jumpingmind.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeersRepository {
    fun getBeers(): Flow<PagingData<BeerInfo>>
}
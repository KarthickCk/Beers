package com.app.jumpingmind.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.app.jumpingmind.data.api.BeersApi
import com.app.jumpingmind.domain.model.Beer
import com.app.jumpingmind.domain.repository.BeersRepository
import com.app.jumpingmind.ui.beers.BeersDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BeersRepositoryImpl @Inject constructor(
    private val beersApi: BeersApi
) : BeersRepository {

    companion object {
        private const val PAGE_COUNT = 20
    }

    override fun getBeers(): Flow<PagingData<Beer>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_COUNT,
                initialLoadSize = PAGE_COUNT,
                prefetchDistance = PAGE_COUNT / 2,
                enablePlaceholders = false
            ),
        ) {
            BeersDataSource(
                beersApi,
                PAGE_COUNT
            )
        }.flow
    }
}
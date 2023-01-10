package com.app.jumpingmind.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.jumpingmind.data.api.BeersApi
import com.app.jumpingmind.data.room.BeerInfo
import com.app.jumpingmind.data.room.BeersDAO
import com.app.jumpingmind.domain.model.Beer

class BeersDataSource(
    private val beersApi: BeersApi,
    private val beersDAO: BeersDAO,
    private val pageCount: Int
) : PagingSource<Int, BeerInfo>() {

    override fun getRefreshKey(state: PagingState<Int, BeerInfo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerInfo> {

        val page = params.key ?: 1

        return try {

            val localCache = beersDAO.getPagedList(params.loadSize, (page - 1) * params.loadSize)
            var beers = emptyList<BeerInfo>()
            if (localCache.isEmpty()) {
                beers = beersApi.getBeers(page).map {
                    BeerInfo(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        imageUrl = it.image_url ?: ""
                    )
                }
                beersDAO.insertAll(beers)
            } else {
                beers = localCache
            }
            val nextKey = if (beers.isEmpty()) {
                null
            } else {
                page + (params.loadSize / pageCount)
            }

            if (beers.isEmpty().not()) {
                LoadResult.Page(beers, if (page == 1) null else page, nextKey)
            } else {
                LoadResult.Page(
                    emptyList(), null, null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(
                e
            )
        }

    }
}
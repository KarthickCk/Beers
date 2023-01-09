package com.app.jumpingmind.ui.beers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.jumpingmind.data.api.BeersApi
import com.app.jumpingmind.domain.model.Beer

class BeersDataSource(
    private val beersApi: BeersApi,
    private val pageCount: Int
) : PagingSource<Int, Beer>() {

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {

        val page = params.key ?: 1

        return try {
            val beers = beersApi.getBeers(page)

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
            LoadResult.Page(
                emptyList(), null, null
            )
        }

    }
}
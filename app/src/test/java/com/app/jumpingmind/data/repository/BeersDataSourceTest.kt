package com.app.jumpingmind.data.repository

import androidx.paging.PagingSource
import com.app.jumpingmind.data.api.BeersApi
import com.app.jumpingmind.domain.model.Beer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BeersDataSourceTest {

    @MockK
    lateinit var beersApi: BeersApi

    private lateinit var beersDataSource: BeersDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        beersDataSource = BeersDataSource(beersApi, 12)
    }

    @Test
    fun `test load success`() = runBlockingTest {

        coEvery { beersApi.getBeers(1) } returns listOf()

        val expected = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )

        assertEquals(
            expected, beersDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 12,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `test load exception`() = runBlockingTest {

        val e = Exception()

        coEvery { beersApi.getBeers(1) } throws e

        val expected = PagingSource.LoadResult.Error<Int, Beer>(
            e
        )

        assertEquals(
            expected, beersDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 12,
                    placeholdersEnabled = false
                )
            )
        )
    }
}
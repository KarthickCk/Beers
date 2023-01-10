package com.app.jumpingmind.data.repository

import androidx.paging.PagingSource
import com.app.jumpingmind.data.api.BeersApi
import com.app.jumpingmind.data.room.BeersDAO
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
    @MockK
    lateinit var beersDAO: BeersDAO

    private lateinit var beersDataSource: BeersDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        beersDataSource = BeersDataSource(beersApi, beersDAO, 12)
    }

    @Test
    fun `test load success`() = runBlockingTest {

        coEvery { beersApi.getBeers(1) } returns listOf()
        coEvery { beersDAO.getPagedList(12, 0) } returns listOf()
        coEvery { beersDAO.insertAll(emptyList()) } returns Unit
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
        coEvery { beersDAO.getPagedList(12, 0) } returns listOf()
        coEvery { beersDAO.insertAll(emptyList()) } returns Unit
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
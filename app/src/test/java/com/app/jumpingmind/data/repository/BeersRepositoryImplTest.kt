package com.app.jumpingmind.data.repository

import androidx.paging.PagingData
import com.app.jumpingmind.data.api.BeersApi
import com.app.jumpingmind.domain.model.Beer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class BeersRepositoryImplTest {

    @MockK
    lateinit var beersApi: BeersApi

    private lateinit var beersRepositoryImpl: BeersRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        beersRepositoryImpl = BeersRepositoryImpl(beersApi)
    }

    @Test
    fun `test beers list`() =  runBlockingTest {
        val result = mutableListOf<PagingData<Beer>>()

        coEvery { beersApi.getBeers(1) } returns listOf()

        val job = launch {
            beersRepositoryImpl.getBeers().toList(result)
        }
        assert(result[0] is PagingData<Beer>)

        job.cancel()
    }
}
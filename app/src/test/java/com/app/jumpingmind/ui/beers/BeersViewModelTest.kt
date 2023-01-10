package com.app.jumpingmind.ui.beers

import androidx.paging.PagingData
import com.app.jumpingmind.MainCoroutineRule
import com.app.jumpingmind.data.room.BeerInfo
import com.app.jumpingmind.domain.model.Beer
import com.app.jumpingmind.domain.repository.BeersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BeersViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @MockK
    lateinit var beersRepository: BeersRepository

    private lateinit var beersViewModel: BeersViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        beersViewModel = BeersViewModel(beersRepository)

        Assert.assertEquals(beersViewModel.uiState.value, PagingData.empty<Beer>())
    }

    @Test
    fun `test get beers`() = runBlockingTest {

        val result = mutableListOf<PagingData<BeerInfo>>()

        coEvery { beersRepository.getBeers() } returns flow { emit(PagingData.from(listOf())) }

        val job = launch {
            beersViewModel.uiState.toList(result)
        }

        beersViewModel.getBeers()

        assert(result[0] is PagingData<BeerInfo>)

        job.cancel()
    }
}
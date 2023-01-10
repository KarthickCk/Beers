package com.app.jumpingmind.ui.beers

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.jumpingmind.data.room.BeerInfo
import com.app.jumpingmind.domain.model.Beer
import com.app.jumpingmind.domain.repository.BeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val beersRepository: BeersRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<PagingData<BeerInfo>>(PagingData.empty())
    val uiState = _uiStateFlow.asStateFlow()

    init {
        getBeers()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getBeers() {
        viewModelScope.launch {

            beersRepository.getBeers()
                .cachedIn(viewModelScope)
                .onEach {
                    _uiStateFlow.value = it
                }
                .collect()
        }
    }
}
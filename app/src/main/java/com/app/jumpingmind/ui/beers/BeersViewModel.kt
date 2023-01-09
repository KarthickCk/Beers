package com.app.jumpingmind.ui.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.jumpingmind.domain.model.Beer
import com.app.jumpingmind.domain.repository.BeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val beersRepository: BeersRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UIState>(UIState.Loading(true))
    val uiState = _uiStateFlow.asStateFlow()

    init {
        getBeers()
    }

    private fun getBeers() {
        viewModelScope.launch {

            beersRepository.getBeers()
                .cachedIn(viewModelScope)
                .onEach {
                    _uiStateFlow.value = UIState.List(it)
                }
                .catch { exception ->
                    _uiStateFlow.value = UIState.Error(exception.message ?: "Error")
                }
                .collect()
        }
    }

    sealed class UIState {
        class List(val list: PagingData<Beer>) : UIState()
        class Loading(val isLoading: Boolean) : UIState()
        class Error(val message: String) : UIState()
    }
}
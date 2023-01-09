package com.app.jumpingmind.ui.beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.jumpingmind.databinding.LayoutBeerListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BeerListFragment : Fragment() {

    private lateinit var binding: LayoutBeerListBinding
    private val beersViewModel by viewModels<BeersViewModel>()
    private val beersListAdapter = BeersListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutBeerListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.beersList.layoutManager = LinearLayoutManager(requireContext())
        binding.beersList.adapter = beersListAdapter

        beersViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    is BeersViewModel.UIState.List -> beersListAdapter.submitData(state.list)

                }
            }
            .launchIn(lifecycleScope)
    }
}
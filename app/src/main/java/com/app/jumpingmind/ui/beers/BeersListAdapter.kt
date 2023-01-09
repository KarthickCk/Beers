package com.app.jumpingmind.ui.beers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.jumpingmind.databinding.LayoutBeerItemBinding
import com.app.jumpingmind.domain.model.Beer

class BeersListAdapter :
    PagingDataAdapter<Beer, BeersListAdapter.BeerViewHolder>(BeerDiffCallBack()) {

    class BeerDiffCallBack : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(
            LayoutBeerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class BeerViewHolder(
        private val binding: LayoutBeerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer?) {
            beer?.let {
                binding.beerName.text = beer.name
            }
        }
    }
}
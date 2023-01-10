package com.app.jumpingmind.ui.beers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.jumpingmind.R
import com.app.jumpingmind.data.room.BeerInfo
import com.app.jumpingmind.databinding.LayoutBeerItemBinding
import com.app.jumpingmind.extension.loadUrl

class BeersListAdapter :
    PagingDataAdapter<BeerInfo, BeersListAdapter.BeerViewHolder>(BeerDiffCallBack()) {

    class BeerDiffCallBack : DiffUtil.ItemCallback<BeerInfo>() {
        override fun areItemsTheSame(oldItem: BeerInfo, newItem: BeerInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BeerInfo, newItem: BeerInfo): Boolean {
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

        fun bind(beer: BeerInfo?) {
            beer?.let {
                binding.beerName.text = beer.name
                binding.beerImage.loadUrl(beer.imageUrl, R.drawable.ic_launcher_foreground)
                binding.beerDescription.text = beer.description
            }
        }
    }
}
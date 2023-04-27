package com.ua.fitfuel.presentation.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ua.fitfuel.data.local.models.entities.FavoritesEntity
import com.ua.fitfuel.databinding.FavoriteListItemBinding
import com.ua.fitfuel.presentation.recipes.diff.RecipeDiff
import com.ua.fitfuel.presentation.recipes.screens.RecipeFavoriteFragmentDirections
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var favorites = emptyList<FavoritesEntity>()

    class FavoriteViewHolder(private val binding: FavoriteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                return FavoriteViewHolder(
                    FavoriteListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(favorite: FavoritesEntity) {
            binding.favorite = favorite
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite)

        holder.itemView.setOnClickListener {
            val action =
                RecipeFavoriteFragmentDirections.actionRecipeFavoriteFragmentToDetailsActivity(
                    favorite.recipe
                )
            it.findNavController().navigate(action)
        }
    }

    fun setNewFavorites(newFavorites: List<FavoritesEntity>) {
        val recipeDiff = RecipeDiff(favorites, newFavorites)
        val recipeDiffResult = DiffUtil.calculateDiff(recipeDiff)
        favorites = newFavorites
        recipeDiffResult.dispatchUpdatesTo(this)
    }
}
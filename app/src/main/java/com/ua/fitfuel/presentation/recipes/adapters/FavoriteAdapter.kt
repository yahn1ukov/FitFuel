package com.ua.fitfuel.presentation.recipes.adapters

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ua.fitfuel.R
import com.ua.fitfuel.data.local.models.entities.FavoritesEntity
import com.ua.fitfuel.databinding.FavoriteListItemBinding
import com.ua.fitfuel.presentation.recipes.diff.RecipeDiff
import com.ua.fitfuel.presentation.recipes.screens.RecipeFavoriteFragmentDirections
import com.ua.fitfuel.presentation.recipes.viewmodels.RecipeFavoriteViewModel
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class FavoriteAdapter(
    private val requireActivity: FragmentActivity,
    private val recipeFavoriteViewModel: RecipeFavoriteViewModel
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(),
    ActionMode.Callback {
    private var favorites = emptyList<FavoritesEntity>()
    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var viewHolders = arrayListOf<FavoriteViewHolder>()
    private lateinit var am: ActionMode

    class FavoriteViewHolder(val binding: FavoriteListItemBinding) :
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
        viewHolders.add(holder)

        val favorite = favorites[position]
        holder.bind(favorite)

        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, favorite)
            } else {
                val action =
                    RecipeFavoriteFragmentDirections.actionRecipeFavoriteFragmentToDetailsActivity(
                        favorite.recipe
                    )
                it.findNavController().navigate(action)
            }
        }

        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, favorite)
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    private fun applySelection(holder: FavoriteViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeSelectedRecipeStyle(holder, android.R.color.white)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeSelectedRecipeStyle(holder, R.color.mediumGray)
            applyActionModeTitle()
        }
    }

    private fun changeSelectedRecipeStyle(
        holder: FavoriteViewHolder,
        background: Int
    ) {
        holder.binding.cardFavoriteRecipe.setCardBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                background
            )
        )
    }


    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> am.finish()
            1 -> am.title = "${selectedRecipes.size} item selected"
            else -> am.title = "${selectedRecipes.size} items selected"
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.fragment_recipe_favorite_contextual_menu, menu)
        am = actionMode!!
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.menu_delete) {
            selectedRecipes.forEach {
                recipeFavoriteViewModel.delete(it)
            }
            multiSelection = false
            selectedRecipes.clear()
            am.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        viewHolders.forEach { holder ->
            changeSelectedRecipeStyle(holder, android.R.color.white)
        }
        multiSelection = false
        selectedRecipes.clear()
    }

    fun setNewFavorites(newFavorites: List<FavoritesEntity>) {
        val recipeDiff = RecipeDiff(favorites, newFavorites)
        val recipeDiffResult = DiffUtil.calculateDiff(recipeDiff)
        favorites = newFavorites
        recipeDiffResult.dispatchUpdatesTo(this)
    }

    fun clearContextualActionMode() {
        if (this::am.isInitialized) {
            am.finish()
        }
    }
}
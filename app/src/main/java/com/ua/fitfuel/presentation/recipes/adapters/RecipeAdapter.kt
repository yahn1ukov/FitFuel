package com.ua.fitfuel.presentation.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ua.fitfuel.data.remote.models.entities.Recipe
import com.ua.fitfuel.data.remote.models.entities.Recipes
import com.ua.fitfuel.databinding.RecipeListItemBinding
import com.ua.fitfuel.presentation.recipes.diff.RecipeDiff
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private var recipes = emptyList<Recipe>()

    class RecipeViewHolder(private val binding: RecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                return RecipeViewHolder(
                    RecipeListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    fun setNewRecipes(newRecipes: Recipes) {
        val recipeDiff = RecipeDiff(recipes, newRecipes.recipes)
        val recipeDiffResult = DiffUtil.calculateDiff(recipeDiff)
        recipes = newRecipes.recipes
        recipeDiffResult.dispatchUpdatesTo(this)
    }
}
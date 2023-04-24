package com.ua.fitfuel.presentation.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ua.fitfuel.data.remote.models.entities.Ingredient
import com.ua.fitfuel.databinding.IngredientListItemBinding
import com.ua.fitfuel.presentation.recipes.diff.RecipeDiff
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private var ingredients = emptyList<Ingredient>()

    class IngredientViewHolder(private val binding: IngredientListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): IngredientViewHolder {
                return IngredientViewHolder(
                    IngredientListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(ingredient: Ingredient) {
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun setNewIngredients(newIngredients: List<Ingredient>) {
        val ingredientDiff = RecipeDiff(ingredients, newIngredients)
        val ingredientDiffResult = DiffUtil.calculateDiff(ingredientDiff)
        ingredients = newIngredients
        ingredientDiffResult.dispatchUpdatesTo(this)
    }
}
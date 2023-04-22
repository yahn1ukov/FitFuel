package com.ua.fitfuel.presentation.recipes.diff

import androidx.recyclerview.widget.DiffUtil
import com.ua.fitfuel.data.remote.models.entities.Recipe

class RecipeDiff(
    private val oldRecipes: List<Recipe>,
    private val newRecipes: List<Recipe>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldRecipes.size
    }

    override fun getNewListSize(): Int {
        return newRecipes.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipes[oldItemPosition] === newRecipes[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipes[oldItemPosition] == newRecipes[newItemPosition]
    }
}
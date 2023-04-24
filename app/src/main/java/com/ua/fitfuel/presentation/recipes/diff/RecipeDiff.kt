package com.ua.fitfuel.presentation.recipes.diff

import androidx.recyclerview.widget.DiffUtil

class RecipeDiff<T>(
    private val oldRecipes: List<T>,
    private val newRecipes: List<T>
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
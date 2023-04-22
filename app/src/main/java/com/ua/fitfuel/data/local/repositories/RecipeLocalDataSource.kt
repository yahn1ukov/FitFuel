package com.ua.fitfuel.data.local.repositories

import androidx.lifecycle.LiveData
import com.ua.fitfuel.data.local.dao.RecipeDao
import com.ua.fitfuel.data.local.models.entities.RecipesEntity
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {
    suspend fun insert(recipes: RecipesEntity) {
        recipeDao.insert(recipes)
    }

    fun getAll(): LiveData<List<RecipesEntity>> {
        return recipeDao.getAll()
    }
}
package com.ua.fitfuel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ua.fitfuel.data.local.models.entities.RecipesEntity

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipes: RecipesEntity)

    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<RecipesEntity>>
}
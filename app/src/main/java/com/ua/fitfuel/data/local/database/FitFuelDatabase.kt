package com.ua.fitfuel.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ua.fitfuel.data.local.dao.RecipeDao
import com.ua.fitfuel.data.local.models.entities.RecipesEntity
import com.ua.fitfuel.data.local.models.typeconverters.RecipesTypeConverter

@Database(entities = [RecipesEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipesTypeConverter::class)
abstract class FitFuelDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
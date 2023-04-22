package com.ua.fitfuel.data.local.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ua.fitfuel.data.remote.models.entities.Recipes
import com.ua.fitfuel.utils.Constants.Companion.TABLE_RECIPES

@Entity(tableName = TABLE_RECIPES)
class RecipesEntity(var recipes: Recipes) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
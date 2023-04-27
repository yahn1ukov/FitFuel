package com.ua.fitfuel.data.local.models.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ua.fitfuel.data.remote.models.entities.Recipe

class FavoritesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRecipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun fromStringToRecipe(recipe: String): Recipe {
        val listType = object : TypeToken<Recipe>() {}.type
        return gson.fromJson(recipe, listType)
    }
}
package com.ua.fitfuel.data.local.models.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ua.fitfuel.data.remote.models.entities.Recipes

class RecipesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRecipesToString(recipes: Recipes): String {
        return gson.toJson(recipes)
    }

    @TypeConverter
    fun fromStringToRecipes(recipes: String): Recipes {
        val listType = object : TypeToken<Recipes>() {}.type
        return gson.fromJson(recipes, listType)
    }
}
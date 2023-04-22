package com.ua.fitfuel.utils

class Constants {
    companion object {
        const val DATA_STORE_NAME = "FitFuel"
        const val PREFERENCE_MEAL_TYPE = "mealType"
        const val PREFERENCE_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCE_DIET_TYPE = "dietType"
        const val PREFERENCE_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCE_IS_NETWORK_AVAILABLE = "isNetworkAvailable"

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = ""
        const val ENDPOINT_RECIPES = "/recipes/complexSearch"

        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        const val DEFAULT_NUMBER = "10"
        const val DEFAULT_TYPE = "main course"
        const val DEFAULT_DIET = "gluten free"
        const val DEFAULT_ADD_RECIPE_INFORMATION = "true"
        const val DEFAULT_FILL_INGREDIENTS = "true"

        const val DEFAULT_TYPE_ID = 0
        const val DEFAULT_DIET_ID = 0
        const val DEFAULT_IS_NETWORK_AVAILABLE = false
    }
}
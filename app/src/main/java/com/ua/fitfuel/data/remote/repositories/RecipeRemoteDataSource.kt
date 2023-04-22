package com.ua.fitfuel.data.remote.repositories

import com.ua.fitfuel.data.remote.api.FitFuelApi
import com.ua.fitfuel.data.remote.models.entities.Recipes
import retrofit2.Response
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(
    private val fitFuelApi: FitFuelApi
) {
    suspend fun getAllRecipes(queries: Map<String, String>): Response<Recipes> {
        return fitFuelApi.getAllRecipes(queries)
    }
}
package com.ua.fitfuel.data.remote.api

import com.ua.fitfuel.data.remote.models.entities.Recipes
import com.ua.fitfuel.utils.Constants.Companion.ENDPOINT_RECIPES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FitFuelApi {
    @GET(ENDPOINT_RECIPES)
    suspend fun getAllRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<Recipes>
}
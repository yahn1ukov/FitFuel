package com.ua.fitfuel.data.repositories

import com.ua.fitfuel.data.remote.repositories.RecipeRemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RecipeRepository @Inject constructor(
    recipeRemoteDataSource: RecipeRemoteDataSource
) {
    val remote = recipeRemoteDataSource
}
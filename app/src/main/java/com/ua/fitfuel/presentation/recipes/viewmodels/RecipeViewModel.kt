package com.ua.fitfuel.presentation.recipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ua.fitfuel.data.remote.models.entities.Recipes
import com.ua.fitfuel.data.repositories.RecipeRepository
import com.ua.fitfuel.utils.Constants.Companion.API_KEY
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_ADD_RECIPE_INFORMATION
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_DIET
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_FILL_INGREDIENTS
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_NUMBER
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_TYPE
import com.ua.fitfuel.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.ua.fitfuel.utils.Constants.Companion.QUERY_API_KEY
import com.ua.fitfuel.utils.Constants.Companion.QUERY_DIET
import com.ua.fitfuel.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.ua.fitfuel.utils.Constants.Companion.QUERY_NUMBER
import com.ua.fitfuel.utils.Constants.Companion.QUERY_TYPE
import com.ua.fitfuel.utils.NetworkHelper
import com.ua.fitfuel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _recipes: MutableLiveData<NetworkResult<Recipes>> = MutableLiveData()
    val recipe: LiveData<NetworkResult<Recipes>>
        get() = _recipes

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            _recipes.value = NetworkResult.Loading()
            if (networkHelper.hasInternetConnection()) {
                try {
                    _recipes.value = NetworkResult.toNetworkResult(
                        recipeRepository.remote.getAllRecipes(applyQueries())
                    )
                } catch (e: Exception) {
                    _recipes.value = NetworkResult.Error("Recipes not found")
                }
            } else {
                _recipes.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }

    private fun applyQueries(): Map<String, String> {
        return mapOf(
            QUERY_NUMBER to DEFAULT_NUMBER,
            QUERY_API_KEY to API_KEY,
            QUERY_TYPE to DEFAULT_TYPE,
            QUERY_DIET to DEFAULT_DIET,
            QUERY_ADD_RECIPE_INFORMATION to DEFAULT_ADD_RECIPE_INFORMATION,
            QUERY_FILL_INGREDIENTS to DEFAULT_FILL_INGREDIENTS
        )
    }
}
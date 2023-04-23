package com.ua.fitfuel.presentation.recipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ua.fitfuel.data.local.models.entities.RecipesEntity
import com.ua.fitfuel.data.remote.models.entities.Recipes
import com.ua.fitfuel.data.repositories.RecipeRepository
import com.ua.fitfuel.utils.NetworkHelper
import com.ua.fitfuel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    val localRecipes = recipeRepository.local.getAll()

    private fun insert(recipes: RecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.local.insert(recipes)
        }
    }

    private val _remoteRecipes: MutableLiveData<NetworkResult<Recipes>> = MutableLiveData()
    val remoteRecipes: LiveData<NetworkResult<Recipes>>
        get() = _remoteRecipes

    private val _searchedRemoteRecipes: MutableLiveData<NetworkResult<Recipes>> = MutableLiveData()
    val searchedRemoteRecipes: LiveData<NetworkResult<Recipes>>
        get() = _searchedRemoteRecipes

    fun getAllRecipes(queries: Map<String, String>) {
        viewModelScope.launch {
            _remoteRecipes.value = NetworkResult.Loading()
            if (networkHelper.hasInternetConnection()) {
                try {
                    val response = recipeRepository.remote.getAllRecipes(queries)
                    _remoteRecipes.value = NetworkResult.toNetworkResult(response)
                    _remoteRecipes.value!!.data?.let { cacheRecipes(it) }
                } catch (e: Exception) {
                    _remoteRecipes.value = NetworkResult.Error("Recipes not found")
                }
            } else {
                _remoteRecipes.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }

    fun searchRecipes(queries: Map<String, String>) {
        viewModelScope.launch {
            _searchedRemoteRecipes.value = NetworkResult.Loading()
            if (networkHelper.hasInternetConnection()) {
                try {
                    _searchedRemoteRecipes.value = NetworkResult.toNetworkResult(
                        recipeRepository.remote.searchRecipes(queries)
                    )
                } catch (e: Exception) {
                    _searchedRemoteRecipes.value = NetworkResult.Error("Recipes not found")
                }
            } else {
                _searchedRemoteRecipes.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }

    private fun cacheRecipes(recipes: Recipes) {
        insert(RecipesEntity(0, recipes))
    }
}
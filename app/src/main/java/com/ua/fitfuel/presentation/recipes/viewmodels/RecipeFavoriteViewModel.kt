package com.ua.fitfuel.presentation.recipes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ua.fitfuel.data.local.models.entities.FavoritesEntity
import com.ua.fitfuel.data.repositories.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeFavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    val localFavorites = favoriteRepository.local.getAll()

    fun insert(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.local.insert(favoritesEntity)
        }
    }

    fun delete(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.local.delete(favoritesEntity)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.local.deleteAll()
        }
    }
}
package com.ua.fitfuel.data.repositories

import com.ua.fitfuel.data.local.repositories.FavoriteLocalDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FavoriteRepository @Inject constructor(
    favoriteLocalDataSource: FavoriteLocalDataSource
) {
    val local = favoriteLocalDataSource
}
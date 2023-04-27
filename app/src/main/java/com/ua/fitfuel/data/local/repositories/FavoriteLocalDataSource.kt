package com.ua.fitfuel.data.local.repositories

import androidx.lifecycle.LiveData
import com.ua.fitfuel.data.local.dao.FavoriteDao
import com.ua.fitfuel.data.local.models.entities.FavoritesEntity
import javax.inject.Inject

class FavoriteLocalDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    fun getAll(): LiveData<List<FavoritesEntity>> {
        return favoriteDao.getAll()
    }

    suspend fun insert(favoritesEntity: FavoritesEntity) {
        favoriteDao.insert(favoritesEntity)
    }

    suspend fun delete(favoritesEntity: FavoritesEntity) {
        favoriteDao.delete(favoritesEntity)
    }

    suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}
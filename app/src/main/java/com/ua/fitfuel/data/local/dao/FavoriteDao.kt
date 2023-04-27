package com.ua.fitfuel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ua.fitfuel.data.local.models.entities.FavoritesEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoritesEntity)

    @Query("SELECT * FROM favorites")
    fun getAll(): LiveData<List<FavoritesEntity>>

    @Delete
    suspend fun delete(favorite: FavoritesEntity)

    @Query("DELETE FROM favorites")
    suspend fun deleteAll()
}
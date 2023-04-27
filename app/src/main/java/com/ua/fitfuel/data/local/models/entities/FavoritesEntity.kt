package com.ua.fitfuel.data.local.models.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ua.fitfuel.data.remote.models.entities.Recipe
import com.ua.fitfuel.utils.Constants.Companion.TABLE_FAVORITES
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_FAVORITES)
@Parcelize
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recipe: Recipe
) : Parcelable
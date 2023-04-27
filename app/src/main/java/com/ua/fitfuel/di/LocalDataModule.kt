package com.ua.fitfuel.di

import android.content.Context
import androidx.room.Room
import com.ua.fitfuel.data.local.dao.FavoriteDao
import com.ua.fitfuel.data.local.dao.RecipeDao
import com.ua.fitfuel.data.local.database.FitFuelDatabase
import com.ua.fitfuel.utils.Constants.Companion.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Singleton
    fun provideFitFuelDatabase(
        @ApplicationContext context: Context
    ): FitFuelDatabase {
        return Room.databaseBuilder(
            context,
            FitFuelDatabase::class.java,
            DATA_BASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(database: FitFuelDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: FitFuelDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}
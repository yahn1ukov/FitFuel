package com.ua.fitfuel.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ua.fitfuel.utils.Constants.Companion.DATA_STORE_NAME
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_DIET
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_DIET_ID
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_IS_NETWORK_AVAILABLE
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_TYPE
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_TYPE_ID
import com.ua.fitfuel.utils.Constants.Companion.PREFERENCE_DIET_TYPE
import com.ua.fitfuel.utils.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.ua.fitfuel.utils.Constants.Companion.PREFERENCE_IS_NETWORK_AVAILABLE
import com.ua.fitfuel.utils.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.ua.fitfuel.utils.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

@ViewModelScoped
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    private object PreferenceKeys {
        val mealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        val mealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val dietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val dietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
        val isNetworkAvailable = booleanPreferencesKey(PREFERENCE_IS_NETWORK_AVAILABLE)
    }

    suspend fun setMealDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.mealType] = mealType
            preferences[PreferenceKeys.mealTypeId] = mealTypeId
            preferences[PreferenceKeys.dietType] = dietType
            preferences[PreferenceKeys.dietTypeId] = dietTypeId
        }
    }

    suspend fun setNetworkAvailable(isNetworkAvailable: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.isNetworkAvailable] = isNetworkAvailable
        }
    }

    val getMealDietType: Flow<MealDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val mealType = preferences[PreferenceKeys.mealType] ?: DEFAULT_TYPE
            val mealTypeId = preferences[PreferenceKeys.mealTypeId] ?: DEFAULT_TYPE_ID
            val dietType = preferences[PreferenceKeys.dietType] ?: DEFAULT_DIET
            val dietTypeId = preferences[PreferenceKeys.dietTypeId] ?: DEFAULT_DIET_ID
            MealDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    val getNetworkAvailable: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferenceKeys.isNetworkAvailable] ?: DEFAULT_IS_NETWORK_AVAILABLE
        }
}

data class MealDietType(
    val mealType: String,
    val mealTypeId: Int,
    val dietType: String,
    val dietTypeId: Int
)
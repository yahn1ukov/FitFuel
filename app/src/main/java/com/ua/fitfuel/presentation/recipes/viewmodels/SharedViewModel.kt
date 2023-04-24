package com.ua.fitfuel.presentation.recipes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
import com.ua.fitfuel.utils.MealDietType
import com.ua.fitfuel.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private lateinit var meadDietType: MealDietType

    val getMealDietType = preferenceManager.getMealDietType.asLiveData(Dispatchers.IO)

    fun setMealDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        meadDietType = MealDietType(mealType, mealTypeId, dietType, dietTypeId)
        viewModelScope.launch(Dispatchers.IO) {
            if (this@SharedViewModel::meadDietType.isInitialized) {
                preferenceManager.setMealDietType(
                    meadDietType.mealType,
                    meadDietType.mealTypeId,
                    meadDietType.dietType,
                    meadDietType.dietTypeId
                )
            }
        }
    }

    fun applyQueries(): Map<String, String> {
        val queries = HashMap<String, String>()

        queries[QUERY_NUMBER] = DEFAULT_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = DEFAULT_ADD_RECIPE_INFORMATION
        queries[QUERY_FILL_INGREDIENTS] = DEFAULT_FILL_INGREDIENTS

        if (this@SharedViewModel::meadDietType.isInitialized) {
            queries[QUERY_TYPE] = meadDietType.mealType
            queries[QUERY_DIET] = meadDietType.dietType
        } else {
            queries[QUERY_TYPE] = DEFAULT_TYPE
            queries[QUERY_DIET] = DEFAULT_DIET
        }

        return queries
    }
}
package com.ua.fitfuel.data.remote.models.entities


import com.google.gson.annotations.SerializedName

data class Recipes(
    @SerializedName("results")
    val recipes: List<Recipe>,
)
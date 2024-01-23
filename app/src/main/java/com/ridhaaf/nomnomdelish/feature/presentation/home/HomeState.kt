package com.ridhaaf.nomnomdelish.feature.presentation.home

import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe

data class HomeState(
    val isRandomRecipeLoading: Boolean = false,
    val randomRecipe: Recipe? = null,
    val randomRecipeError: String = "",
)
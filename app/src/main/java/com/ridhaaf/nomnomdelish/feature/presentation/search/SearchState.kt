package com.ridhaaf.nomnomdelish.feature.presentation.search

import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe

data class SearchState(
    val isSearchRecipesLoading: Boolean = false,
    val searchRecipes: Recipe? = null,
    val searchRecipesError: String = "",
)

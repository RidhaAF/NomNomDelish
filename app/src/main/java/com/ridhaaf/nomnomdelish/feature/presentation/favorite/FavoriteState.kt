package com.ridhaaf.nomnomdelish.feature.presentation.favorite

import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe

data class FavoriteState(
    val isLoading: Boolean = false,
    val favoriteRecipes: List<String>? = null,
    val error: String = "",
    val isRecipesLoading: Boolean = false,
    val recipes: List<Recipe>? = null,
    val recipesError: String = "",
)

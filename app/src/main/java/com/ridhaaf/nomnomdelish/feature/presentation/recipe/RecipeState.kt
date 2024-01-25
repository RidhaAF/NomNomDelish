package com.ridhaaf.nomnomdelish.feature.presentation.recipe

import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe

data class RecipeState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val error: String = "",
    val isFavoriteRecipesLoading: Boolean = false,
    val favoriteRecipes: List<String>? = emptyList(),
    val favoriteRecipesError: String = "",
)

package com.ridhaaf.nomnomdelish.feature.presentation.recipe

sealed class RecipeEvent {
    data class GetRecipeEvent(val id: String) : RecipeEvent()
    data object GetFavoriteRecipesEvent : RecipeEvent()
    data class OnAddToFavoriteClick(val id: String) : RecipeEvent()
    data class OnRemoveFromFavoriteClick(val id: String) : RecipeEvent()
}
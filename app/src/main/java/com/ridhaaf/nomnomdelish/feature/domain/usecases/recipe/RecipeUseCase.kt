package com.ridhaaf.nomnomdelish.feature.domain.usecases.recipe

import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe
import com.ridhaaf.nomnomdelish.feature.domain.repositories.recipe.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeUseCase(private val repository: RecipeRepository) {
    fun getRandomRecipe(): Flow<Resource<Recipe>> {
        return repository.getRandomRecipe()
    }

    fun getRecipeById(id: String): Flow<Resource<Recipe>> {
        return repository.getRecipeById(id)
    }

    fun searchRecipe(query: String): Flow<Resource<Recipe>> {
        return repository.searchRecipe(query)
    }

    suspend fun addFavoriteRecipe(id: String) {
        repository.addFavoriteRecipe(id)
    }

    suspend fun removeFavoriteRecipe(id: String) {
        repository.removeFavoriteRecipe(id)
    }

    fun getFavoriteRecipes(): Flow<Resource<List<String>>> {
        return repository.getFavoriteRecipes()
    }
}
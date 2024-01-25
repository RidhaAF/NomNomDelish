package com.ridhaaf.nomnomdelish.feature.domain.repositories.recipe

import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRandomRecipe(): Flow<Resource<Recipe>>

    fun getRecipeById(id: String): Flow<Resource<Recipe>>

    fun searchRecipe(query: String): Flow<Resource<Recipe>>

    suspend fun addFavoriteRecipe(id: String)

    suspend fun removeFavoriteRecipe(id: String)

    fun getFavoriteRecipes(): Flow<Resource<List<String>>>
}
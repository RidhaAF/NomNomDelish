package com.ridhaaf.nomnomdelish.feature.data.repositories.recipe

import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.datasources.remote.api.recipe.RecipeApi
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe
import com.ridhaaf.nomnomdelish.feature.domain.repositories.recipe.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val apiKey: String,
) : RecipeRepository {
    override fun getRandomRecipe(): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val randomRecipe = api.getRandomRecipe(apiKey)
            emit(Resource.Success(randomRecipe.toRecipe()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override fun getRecipeById(id: String): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipe = api.getRecipeById(apiKey, id)
            emit(Resource.Success(recipe.toRecipe()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override fun searchRecipe(query: String): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipe = api.searchRecipe(apiKey, query)
            emit(Resource.Success(recipe.toRecipe()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }
}
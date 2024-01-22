package com.ridhaaf.nomnomdelish.feature.data.repositories.recipe

import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.datasources.remote.api.recipe.RecipeApi
import com.ridhaaf.nomnomdelish.feature.data.models.Recipe
import com.ridhaaf.nomnomdelish.feature.domain.repositories.recipe.RecipeRepository
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
) : RecipeRepository {
    private val dotenv = dotenv()
    private val apiKey = dotenv["API_KEY"]
    override fun getRandomRecipe(): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val randomRecipe = api.getRandomRecipe(apiKey)
            emit(Resource.Success(randomRecipe))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override fun getRecipeById(id: String): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipe = api.getRecipeById(apiKey, id)
            emit(Resource.Success(recipe))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override fun searchRecipe(query: String): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipe = api.searchRecipe(apiKey, query)
            emit(Resource.Success(recipe))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }
}
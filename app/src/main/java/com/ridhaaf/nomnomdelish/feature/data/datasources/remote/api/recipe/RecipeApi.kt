package com.ridhaaf.nomnomdelish.feature.data.datasources.remote.api.recipe

import com.ridhaaf.nomnomdelish.feature.data.datasources.remote.dto.recipe.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("{apiKey}/random.php")
    suspend fun getRandomRecipe(
        @Path("apiKey") apiKey: String,
    ): RecipeDto

    @GET("{apiKey}/lookup.php?i={id}")
    suspend fun getRecipeById(
        @Path("apiKey") apiKey: String,
        @Path("id") id: String,
    ): RecipeDto

    @GET("{apiKey}/search.php")
    suspend fun searchRecipe(
        @Path("apiKey") apiKey: String,
        @Query("s") query: String,
    ): RecipeDto
}
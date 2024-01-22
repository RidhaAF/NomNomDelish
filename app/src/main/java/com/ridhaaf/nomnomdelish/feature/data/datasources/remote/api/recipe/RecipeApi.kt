package com.ridhaaf.nomnomdelish.feature.data.datasources.remote.api.recipe

import com.ridhaaf.nomnomdelish.feature.data.models.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("/{apiKey}/random.php")
    fun getRandomRecipe(
        @Path("apiKey") apiKey: String,
    ): Recipe

    @GET("/{apiKey}/lookup.php?i={id}")
    fun getRecipeById(
        @Path("apiKey") apiKey: String,
        @Path("id") id: String,
    ): Recipe

    @GET("/{apiKey}/search.php?s={query}")
    fun searchRecipe(
        @Path("apiKey") apiKey: String,
        @Path("query") query: String,
    ): Recipe
}
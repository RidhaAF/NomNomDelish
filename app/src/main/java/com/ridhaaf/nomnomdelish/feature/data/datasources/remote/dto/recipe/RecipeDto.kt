package com.ridhaaf.nomnomdelish.feature.data.datasources.remote.dto.recipe

import com.google.gson.annotations.SerializedName
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe

data class RecipeDto(
    @SerializedName("meals") val meals: List<MealDto>,
) {
    fun toRecipe(): Recipe {
        return Recipe(
            meals = meals.map { it.toMeal() },
        )
    }
}

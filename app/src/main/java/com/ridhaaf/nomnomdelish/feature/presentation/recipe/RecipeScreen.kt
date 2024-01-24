package com.ridhaaf.nomnomdelish.feature.presentation.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Meal
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultBackButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultProgressIndicator
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.RecipeImage
import com.ridhaaf.nomnomdelish.feature.presentation.components.RecipeSubtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel(),
    id: String,
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val meal = state.recipe?.meals?.firstOrNull()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getRecipe(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = meal?.strMeal ?: "Recipe",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    DefaultBackButton(navController)
                },
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Add to favorite",
                    )
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues),
        ) {
            if (state.isLoading) {
                DefaultProgressIndicator()
            } else {
                Column(
                    modifier = modifier.verticalScroll(scrollState),
                ) {
                    RecipeImage(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        meal = meal,
                    )
                    Column(
                        modifier = modifier.padding(16.dp),
                    ) {
                        RecipeTitle(meal = meal)
                        DefaultSpacer(size = 8)
                        RecipeSubtitle(meal = meal)
                        DefaultSpacer()
                        RecipeIngredients(meal = meal)
                        DefaultSpacer()
                        RecipeInstructions(meal = meal)
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeTitle(meal: Meal?) {
    Text(
        text = meal?.strMeal ?: "",
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun RecipeIngredients(meal: Meal?) {
    Text(
        text = "Ingredients",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
    DefaultSpacer(size = 8)
    val ingredientsList = listOf(
        meal?.strMeasure1 to meal?.strIngredient1,
        meal?.strMeasure2 to meal?.strIngredient2,
        meal?.strMeasure3 to meal?.strIngredient3,
        meal?.strMeasure4 to meal?.strIngredient4,
        meal?.strMeasure5 to meal?.strIngredient5,
        meal?.strMeasure6 to meal?.strIngredient6,
        meal?.strMeasure7 to meal?.strIngredient7,
        meal?.strMeasure8 to meal?.strIngredient8,
        meal?.strMeasure9 to meal?.strIngredient9,
        meal?.strMeasure10 to meal?.strIngredient10,
        meal?.strMeasure11 to meal?.strIngredient11,
        meal?.strMeasure12 to meal?.strIngredient12,
        meal?.strMeasure13 to meal?.strIngredient13,
        meal?.strMeasure14 to meal?.strIngredient14,
        meal?.strMeasure15 to meal?.strIngredient15,
        meal?.strMeasure16 to meal?.strIngredient16,
        meal?.strMeasure17 to meal?.strIngredient17,
        meal?.strMeasure18 to meal?.strIngredient18,
        meal?.strMeasure19 to meal?.strIngredient19,
        meal?.strMeasure20 to meal?.strIngredient20,
    )

    for ((measure, ingredient) in ingredientsList) {
        if (!measure.isNullOrBlank() && !ingredient.isNullOrBlank()) {
            IngredientRow(measure = measure, ingredient = ingredient)
        }
    }
}

@Composable
fun RecipeInstructions(meal: Meal?) {
    Text(
        text = "Instructions",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
    DefaultSpacer(size = 8)
    val instructionsList = meal?.strInstructions?.split(". ") ?: emptyList()

    for (instruction in instructionsList) {
        if (instruction.isNotBlank()) {
            Text(text = "$instruction.")
        }
    }
}

@Composable
private fun IngredientRow(
    modifier: Modifier = Modifier,
    measure: String,
    ingredient: String,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            modifier = modifier.weight(0.5f),
            text = measure,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            modifier = modifier.weight(1f),
            text = ingredient,
        )
    }
}
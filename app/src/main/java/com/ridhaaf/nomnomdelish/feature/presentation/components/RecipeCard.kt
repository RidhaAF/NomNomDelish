package com.ridhaaf.nomnomdelish.feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Meal

@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
    meal: Meal?,
    height: Int = 320,
    horizontal: Boolean = false,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(height.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clip(RoundedCornerShape(16.dp))
            .shadow(4.dp),
    ) {
        if (!horizontal) {
            VerticalRecipeCard(
                modifier = modifier,
                meal = meal,
            )
        } else {
            HorizontalRecipeCard(
                modifier = modifier,
                meal = meal,
            )
        }
    }
}

@Composable
fun VerticalRecipeCard(
    modifier: Modifier = Modifier,
    meal: Meal?,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        RecipeImage(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            meal = meal,
        )
        DefaultSpacer()
        RecipeTitle(meal)
        DefaultSpacer(size = 8)
        RecipeSubtitle(
            modifier = modifier,
            meal = meal,
        )
    }
}

@Composable
fun HorizontalRecipeCard(
    modifier: Modifier,
    meal: Meal?,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        RecipeImage(
            modifier = modifier
                .height(64.dp)
                .width(64.dp),
            meal = meal,
        )
        DefaultSpacer(horizontal = true)
        Column {
            RecipeTitle(meal)
            DefaultSpacer(size = 8)
            RecipeSubtitle(
                modifier = modifier,
                meal = meal,
            )
        }
    }
}

@Composable
fun RecipeImage(
    modifier: Modifier = Modifier,
    meal: Meal?,
) {
    AsyncImage(
        model = meal?.strMealThumb,
        contentDescription = meal?.strMeal,
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun RecipeTitle(meal: Meal?) {
    Text(
        text = meal?.strMeal ?: "",
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun RecipeSubtitle(
    modifier: Modifier = Modifier,
    meal: Meal?,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "🍽️ ${meal?.strCategory}",
            color = Color.LightGray,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = "📍${meal?.strArea}",
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
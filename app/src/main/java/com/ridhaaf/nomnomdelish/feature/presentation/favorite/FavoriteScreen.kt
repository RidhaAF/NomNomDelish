package com.ridhaaf.nomnomdelish.feature.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.Default404
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultProgressIndicator
import com.ridhaaf.nomnomdelish.feature.presentation.components.RecipeCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val refreshing = viewModel.isRefreshing.value
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.refresh()
        },
    )

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .padding(16.dp),
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            FavoriteRecipeContent(
                state = state,
                navController = navController,
            )
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun FavoriteRecipeContent(
    state: FavoriteState,
    navController: NavController?,
) {
    val recipes = state.recipes?.meals.orEmpty()

    if (state.isLoading) {
        DefaultProgressIndicator()
    } else {
        if (recipes.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(recipes.size) { index ->
                    val meal = recipes[index]

                    RecipeCard(
                        meal = meal,
                        height = 96,
                        horizontal = true,
                        onClick = {
                            navController?.navigate("recipe/${meal.idMeal}")
                        },
                    )
                }
            }
        } else {
            Default404()
        }
    }
}
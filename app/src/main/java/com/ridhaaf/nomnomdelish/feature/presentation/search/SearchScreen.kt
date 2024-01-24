package com.ridhaaf.nomnomdelish.feature.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.Default404
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultBackButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultProgressIndicator
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField
import com.ridhaaf.nomnomdelish.feature.presentation.components.RecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Search")
                },
                navigationIcon = {
                    DefaultBackButton(navController)
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues),
        ) {
            Column(
                modifier = modifier.padding(16.dp),
            ) {
                SearchTextField(viewModel)
                DefaultSpacer()
                SearchResults(
                    viewModel = viewModel,
                    state = state,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun SearchTextField(viewModel: SearchViewModel) {
    DefaultTextField(
        value = viewModel.searchRecipes.value,
        onValueChange = viewModel::searchRecipes,
        placeholder = "Search recipes...",
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
            )
        },
    )
}

@Composable
fun SearchResults(
    viewModel: SearchViewModel,
    state: SearchState,
    navController: NavController?,
) {
    if (viewModel.searchRecipes.value.isEmpty()) {
        Default404(
            icon = Icons.Rounded.Search,
            title = "Search for recipes",
            subtitle = "Find your favorite recipes",
        )
    } else {
        SearchResultsContent(
            state = state,
            navController = navController,
        )
    }
}

@Composable
fun SearchResultsContent(
    state: SearchState,
    navController: NavController?,
) {
    val recipes = state.searchRecipes?.meals.orEmpty()

    if (state.isSearchRecipesLoading) {
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
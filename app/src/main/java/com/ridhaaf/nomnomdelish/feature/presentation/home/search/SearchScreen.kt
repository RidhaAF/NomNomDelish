package com.ridhaaf.nomnomdelish.feature.presentation.home.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField

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
                    IconButton(onClick = {
                        navController?.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
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
                SearchResults(state)
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
fun SearchResults(state: SearchState) {
    val recipes = state.searchRecipes?.meals.orEmpty()

    if (state.isSearchRecipesLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center),
        )
    } else {
        if (recipes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
            ) {
                items(recipes.size) { index ->
                    val recipe = recipes[index]
                    Text(text = recipe.strMeal ?: "")
                }
            }
        } else {
            Text(text = "No results found")
        }
    }
}
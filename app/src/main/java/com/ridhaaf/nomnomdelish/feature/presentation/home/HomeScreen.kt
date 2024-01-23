package com.ridhaaf.nomnomdelish.feature.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField
import com.ridhaaf.nomnomdelish.feature.presentation.routes.Routes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.randomRecipeError
    val refreshing = viewModel.isRefreshing.value
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.refresh()
        },
    )
    val verticalScrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = error) {
        if (error.isNotEmpty()) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .verticalScroll(verticalScrollState),
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            IntroSection()
            DefaultSpacer()
            SearchSection(navController)
            RandomRecipeCard(state)
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
fun IntroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp,
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(16.dp),
        ) {
            Text(
                text = "Welcome! 👋🏻",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
            )
            DefaultSpacer(size = 4)
            Text(
                text = "Find your favorite recipes here!",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun SearchSection(navController: NavController?) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController?.navigate(Routes.SEARCH)
            },
    ) {
        DefaultTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            placeholder = "Search recipes...",
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                )
            },
        )
    }
}

@Composable
fun RandomRecipeCard(state: HomeState) {
    val recipe = state.randomRecipe
    val meal = recipe?.meals?.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = "Random Recipe",
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )
        DefaultSpacer(size = 8)
        if (state.isRandomRecipeLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center),
            )
        } else {
            if (recipe != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(
                            min = 280.dp,
                            max = 320.dp,
                        )
                        .background(MaterialTheme.colorScheme.surface)
                        .clip(RoundedCornerShape(16.dp))
                        .shadow(4.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        AsyncImage(
                            model = meal?.strMealThumb,
                            contentDescription = meal?.strMeal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                        )
                        DefaultSpacer()
                        Text(
                            text = meal?.strMeal ?: "",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        DefaultSpacer(size = 8)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
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
                }
            } else {
                Text(
                    text = "No recipe found!",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
package com.ridhaaf.nomnomdelish.feature.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultBottomNavigation
import com.ridhaaf.nomnomdelish.feature.presentation.favorite.FavoriteScreen
import com.ridhaaf.nomnomdelish.feature.presentation.home.HomeScreen
import com.ridhaaf.nomnomdelish.feature.presentation.profile.ProfileScreen
import com.ridhaaf.nomnomdelish.feature.presentation.routes.Routes

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            DefaultBottomNavigation(currentDestination, navController)
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = modifier.padding(paddingValues),
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
            composable(Routes.FAVORITE) {
                FavoriteScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
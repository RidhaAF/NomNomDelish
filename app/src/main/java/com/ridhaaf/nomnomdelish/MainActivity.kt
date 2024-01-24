package com.ridhaaf.nomnomdelish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in.SignInScreen
import com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up.SignUpScreen
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultBottomNavigation
import com.ridhaaf.nomnomdelish.feature.presentation.favorite.FavoriteScreen
import com.ridhaaf.nomnomdelish.feature.presentation.home.HomeScreen
import com.ridhaaf.nomnomdelish.feature.presentation.search.SearchScreen
import com.ridhaaf.nomnomdelish.feature.presentation.profile.ProfileScreen
import com.ridhaaf.nomnomdelish.feature.presentation.routes.Routes
import com.ridhaaf.nomnomdelish.ui.theme.NomNomDelishTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomNomDelishTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (currentDestination?.route !in listOf(Routes.SIGN_IN, Routes.SIGN_UP)) {
                DefaultBottomNavigation(currentDestination, navController)
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.SIGN_IN,
            modifier = modifier.padding(paddingValues),
        ) {
            composable(Routes.SIGN_IN) {
                SignInScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
            composable(Routes.SIGN_UP) {
                SignUpScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
            composable(Routes.HOME) {
                HomeScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
            composable(Routes.SEARCH) {
                SearchScreen(
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
fun AppPreview() {
    NomNomDelishTheme {
        App()
    }
}
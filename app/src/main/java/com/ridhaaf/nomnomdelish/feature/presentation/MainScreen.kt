package com.ridhaaf.nomnomdelish.feature.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultBottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentDestination: NavDestination?,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            DefaultBottomNavigation(currentDestination, navController)
        },
        content = {},
    )
}
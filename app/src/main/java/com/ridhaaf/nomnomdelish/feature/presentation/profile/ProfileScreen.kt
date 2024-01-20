package com.ridhaaf.nomnomdelish.feature.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.routes.Routes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = state.isSignOutSuccess) {
        if (state.isSignOutSuccess) {
            navController?.navigate(Routes.SIGN_IN) {
                popUpTo(Routes.SIGN_IN) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        SignOutButton(
            viewModel = viewModel,
            state = state,
        )
    }
}

@Composable
fun SignOutButton(
    viewModel: ProfileViewModel,
    state: ProfileState,
) {
    val text = if (state.isSignOutLoading) "Signing out.." else "Sign out"

    DefaultButton(
        onClick = {
            viewModel.onEvent(ProfileEvent.OnSignOutClick)
        },
        child = {
            Text(text = text)
        },
    )
}
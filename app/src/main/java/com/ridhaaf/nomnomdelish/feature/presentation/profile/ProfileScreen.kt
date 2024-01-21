package com.ridhaaf.nomnomdelish.feature.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.data.models.User
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultPhotoProfile
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        ProfileContent(state)
        DefaultSpacer()
        SignOutButton(
            viewModel = viewModel,
            state = state,
        )
    }
}

@Composable
fun PhotoProfile(user: User) {
    DefaultPhotoProfile(user = user)
}

@Composable
fun NameProfile(user: User) {
    Text(text = user.name)
}

@Composable
fun EmailProfile(user: User) {
    Text(
        text = user.email,
        color = Color.Gray,
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
) {
    val user: User = state.user

    if (state.isProfileLoading) {
        CircularProgressIndicator()
        return
    }
    Row {
        PhotoProfile(user)
        DefaultSpacer(
            horizontal = true,
            size = 16,
        )
        Column {
            NameProfile(user)
            EmailProfile(user)
        }
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
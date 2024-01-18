package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

data class SignInWithGoogleState(
    val isLoading: Boolean = false,
    val isSignInWithGoogleSuccess: Boolean = false,
    val error: String = "",
)
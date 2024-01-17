package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

data class SignInState(
    val isLoading: Boolean = false,
    val isSignInSuccess: Boolean = false,
    val error: String = "",
)
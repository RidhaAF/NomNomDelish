package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up

data class SignUpWithGoogleState(
    val isLoading: Boolean = false,
    val isSignUpWithGoogleSuccess: Boolean = false,
    val error: String = "",
)
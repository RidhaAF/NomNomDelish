package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up

data class SignUpState(
    val isLoading: Boolean = false,
    val isSignUpSuccess: Boolean = false,
    val error: String = "",
)
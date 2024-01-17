package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

sealed class SignInEvent {
    data class OnEmailChange(val email: String) : SignInEvent()
    data class OnPasswordChange(val password: String) : SignInEvent()
    data object OnSignInClick : SignInEvent()
}
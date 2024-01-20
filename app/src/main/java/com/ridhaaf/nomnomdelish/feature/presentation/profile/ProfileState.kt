package com.ridhaaf.nomnomdelish.feature.presentation.profile

data class ProfileState(
    val isProfileLoading: Boolean = false,
    val isProfileSuccess: Boolean = false,
    val isProfileError: String = "",
    val isSignOutLoading: Boolean = false,
    val isSignOutSuccess: Boolean = false,
    val isSignOutError: String = "",
)
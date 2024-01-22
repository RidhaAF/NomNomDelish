package com.ridhaaf.nomnomdelish.feature.presentation.profile

import com.ridhaaf.nomnomdelish.feature.data.models.User

data class ProfileState(
    val user: User = User(),
    val isProfileLoading: Boolean = false,
    val isProfileSuccess: Boolean = false,
    val isProfileError: String = "",
    val isSignOutLoading: Boolean = false,
    val isSignOutSuccess: Boolean = false,
    val isSignOutError: String = "",
)
package com.ridhaaf.nomnomdelish.feature.presentation.profile

sealed class ProfileEvent {
    data object OnSignOutClick : ProfileEvent()
}
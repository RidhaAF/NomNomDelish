package com.ridhaaf.nomnomdelish.feature.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.models.auth.User
import com.ridhaaf.nomnomdelish.feature.domain.usecases.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {
    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            useCase.getCurrentUser().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = ProfileState(
                            isProfileLoading = true,
                            isProfileSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = ProfileState(
                            user = result.data ?: User(),
                            isProfileLoading = false,
                            isProfileSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = ProfileState(
                            isProfileLoading = false,
                            isProfileSuccess = false,
                            isProfileError = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            useCase.signOut().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = ProfileState(
                            isSignOutLoading = true,
                            isSignOutSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = ProfileState(
                            isSignOutLoading = false,
                            isSignOutSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = ProfileState(
                            isSignOutLoading = false,
                            isSignOutSuccess = false,
                            isSignOutError = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnSignOutClick -> {
                signOut()
            }
        }
    }
}
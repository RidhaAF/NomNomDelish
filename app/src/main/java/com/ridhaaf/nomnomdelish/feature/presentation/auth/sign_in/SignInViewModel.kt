package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.domain.usecases.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    private val _googleState = mutableStateOf(SignInWithGoogleState())
    val googleState: State<SignInWithGoogleState> = _googleState

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private var isAuthenticated by mutableStateOf(false)

    fun isAuth(): Boolean {
        viewModelScope.launch {
            useCase.isAuthenticated().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignInState(
                            isLoading = true,
                            isSignInSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        isAuthenticated = result.data ?: false
                        _state.value = SignInState(
                            isLoading = false,
                            isSignInSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = SignInState(
                            isLoading = false,
                            isSignInSuccess = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
        return isAuthenticated
    }

    private fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            useCase.signIn(email, password).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignInState(
                            isLoading = true,
                            isSignInSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = SignInState(
                            isLoading = false,
                            isSignInSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = SignInState(
                            isLoading = false,
                            isSignInSuccess = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
    }

    fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            useCase.signInWithGoogle(credential).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _googleState.value = SignInWithGoogleState(
                            isLoading = true,
                            isSignInWithGoogleSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        _googleState.value = SignInWithGoogleState(
                            isLoading = false,
                            isSignInWithGoogleSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _googleState.value = SignInWithGoogleState(
                            isLoading = false,
                            isSignInWithGoogleSuccess = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> {
                email = event.email
            }

            is SignInEvent.OnPasswordChange -> {
                password = event.password
            }

            is SignInEvent.OnSignInClick -> {
                if (email.isEmpty() || password.isEmpty()) {
                    _state.value = SignInState(
                        error = "Please fill in the fields",
                    )
                    return
                }
                signIn(email, password)
            }
        }
    }
}
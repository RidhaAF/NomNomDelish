package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up

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
class SignUpViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {
    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    private val _googleState = mutableStateOf(SignUpWithGoogleState())
    val googleState: State<SignUpWithGoogleState> = _googleState

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    private var isAuthenticated by mutableStateOf(false)

    fun isAuth(): Boolean {
        viewModelScope.launch {
            useCase.isAuthenticated().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignUpState(
                            isLoading = true,
                            isSignUpSuccess = false,
                        )
                        _googleState.value = SignUpWithGoogleState(
                            isLoading = true,
                            isSignUpWithGoogleSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        isAuthenticated = result.data ?: false
                        _state.value = SignUpState(
                            isLoading = false,
                            isSignUpSuccess = isAuthenticated,
                        )
                        _googleState.value = SignUpWithGoogleState(
                            isLoading = false,
                            isSignUpWithGoogleSuccess = isAuthenticated,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = SignUpState(
                            isLoading = false,
                            isSignUpSuccess = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                        _googleState.value = SignUpWithGoogleState(
                            isLoading = false,
                            isSignUpWithGoogleSuccess = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
        return isAuthenticated
    }

    private fun signUp(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            useCase.signUp(name, email, password).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignUpState(
                            isLoading = true,
                            isSignUpSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = SignUpState(
                            isLoading = false,
                            isSignUpSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = SignUpState(
                            isLoading = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
    }

    fun signUpWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            useCase.signInWithGoogle(credential).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _googleState.value = SignUpWithGoogleState(
                            isLoading = true,
                            isSignUpWithGoogleSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        _googleState.value = SignUpWithGoogleState(
                            isLoading = false,
                            isSignUpWithGoogleSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        _googleState.value = SignUpWithGoogleState(
                            isLoading = false,
                            isSignUpWithGoogleSuccess = false,
                            error = result.message ?: "An unknown error occurred",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnNameChange -> {
                name = event.name
            }

            is SignUpEvent.OnEmailChange -> {
                email = event.email
            }

            is SignUpEvent.OnPasswordChange -> {
                password = event.password
            }

            is SignUpEvent.OnConfirmPasswordChange -> {
                confirmPassword = event.confirmPassword
            }

            is SignUpEvent.OnSignUpClick -> {
                if (password != confirmPassword) {
                    _state.value = SignUpState(
                        error = "Password and confirm password must be the same"
                    )
                    return
                } else if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    _state.value = SignUpState(
                        error = "Please fill in all the fields"
                    )
                    return
                }
                signUp(name, email, password)
            }
        }
    }

    fun resetState() {
        _state.value = SignUpState()
        _googleState.value = SignUpWithGoogleState()
    }
}
package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.domain.usecases.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val application: Application,
    private val useCase: AuthUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            useCase.signIn(email, password).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignInState(isLoading = true)
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
                            error = result.message ?: "An unknown error occurred",
                        )
                        Toast.makeText(
                            application,
                            result.message ?: "An unknown error occurred",
                            Toast.LENGTH_LONG,
                        ).show()
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
                    Toast.makeText(
                        application,
                        "Please fill in the fields",
                        Toast.LENGTH_LONG,
                    ).show()
                    return
                }
                signIn(email, password)
            }
        }
    }
}
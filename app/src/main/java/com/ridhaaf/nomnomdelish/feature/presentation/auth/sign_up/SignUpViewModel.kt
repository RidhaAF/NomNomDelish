package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.domain.usecases.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCase: AuthUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun signUp(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            Log.d("SignUpViewModel", "signUp: $name, $email, $password")
            useCase.signUp(name, email, password).collectLatest { result ->
                Log.d("SignUpViewModel", "signUp: $result")
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignUpState(isLoading = true)
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
}
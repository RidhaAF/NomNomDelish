package com.ridhaaf.nomnomdelish.feature.presentation.recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.domain.usecases.recipe.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val useCase: RecipeUseCase) : ViewModel() {
    private val _state = mutableStateOf(RecipeState())
    val state: State<RecipeState> = _state

    fun getRecipe(id: String) {
        viewModelScope.launch {
            useCase.getRecipeById(id).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            recipe = null,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            recipe = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            recipe = null,
                            error = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }
}
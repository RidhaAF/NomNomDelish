package com.ridhaaf.nomnomdelish.feature.presentation.favorite

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
class FavoriteViewModel @Inject constructor(private val useCase: RecipeUseCase) : ViewModel() {
    private val _state = mutableStateOf(FavoriteState())
    val state: State<FavoriteState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() {
        getFavoriteRecipes()
    }

    private fun getFavoriteRecipes() {
        viewModelScope.launch {
            useCase.getFavoriteRecipes().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            favoriteRecipes = emptyList(),
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            favoriteRecipes = result.data,
                        )
                        _state.value.favoriteRecipes?.forEach { id ->
                            getRecipe(id)
                        }
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            favoriteRecipes = emptyList(),
                            error = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun getRecipe(id: String) {
        viewModelScope.launch {
            useCase.getRecipeById(id).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isRecipesLoading = true,
                            recipes = null,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isRecipesLoading = false,
                            recipes = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isRecipesLoading = false,
                            recipes = null,
                            recipesError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }
}
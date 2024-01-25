package com.ridhaaf.nomnomdelish.feature.presentation.home

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
class HomeViewModel @Inject constructor(private val useCase: RecipeUseCase) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        getRandomRecipe()
    }

    fun refresh() {
        getRandomRecipe()
    }

    private fun getRandomRecipe() {
        viewModelScope.launch {
            useCase.getRandomRecipe().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = HomeState(
                            isRandomRecipeLoading = true,
                            randomRecipe = null,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = HomeState(
                            isRandomRecipeLoading = false,
                            randomRecipe = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = HomeState(
                            isRandomRecipeLoading = false,
                            randomRecipe = null,
                            randomRecipeError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }
}
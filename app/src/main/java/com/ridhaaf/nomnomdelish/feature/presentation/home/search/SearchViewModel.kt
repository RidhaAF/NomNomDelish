package com.ridhaaf.nomnomdelish.feature.presentation.home.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.domain.usecases.recipe.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: RecipeUseCase) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _searchRecipes = mutableStateOf("")
    val searchRecipes: State<String> = _searchRecipes

    private var searchJob: Job? = null

    fun searchRecipes(query: String) {
        _searchRecipes.value = query

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            useCase.searchRecipe(query).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isSearchRecipesLoading = true,
                            searchRecipes = null,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isSearchRecipesLoading = false,
                            searchRecipes = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isSearchRecipesLoading = false,
                            searchRecipes = result.data,
                            searchRecipesError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}
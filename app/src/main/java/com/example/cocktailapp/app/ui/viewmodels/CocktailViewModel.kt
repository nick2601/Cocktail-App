package com.example.cocktailapp.app.ui.viewmodels

import Cocktail
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.remote.models.Category
import com.example.cocktailapp.domain.usecases.GetCategoriesByNameUseCase
import com.example.cocktailapp.domain.usecases.GetCategoriesUseCase
import com.example.cocktailapp.domain.usecases.GetCocktailByIdUseCase
import com.example.cocktailapp.domain.usecases.GetCocktailsByNameUseCase
import com.example.cocktailapp.domain.usecases.RandomCocktailUseCase
import com.example.cocktailapp.domain.usecases.SearchIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val getCocktailsByNameUseCase: GetCocktailsByNameUseCase,
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchIngredientUseCase: SearchIngredientUseCase,
    private val randomCocktailUseCase: RandomCocktailUseCase,
    private val filterCocktailsByCategories: GetCategoriesByNameUseCase

) : ViewModel() {

    private val _cocktails = MutableStateFlow<List<Cocktail>>(emptyList())
    val cocktails = _cocktails.asStateFlow()

    private val _selectedCocktail = MutableStateFlow<Cocktail?>(null)
    val selectedCocktail = _selectedCocktail.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    fun fetchCocktailsByName(name: String) = viewModelScope.launch {
        _cocktails.value = getCocktailsByNameUseCase(name)
    }

    fun fetchCocktailById(id: String) = viewModelScope.launch {
        _selectedCocktail.value = getCocktailByIdUseCase(id)
    }

    fun fetchRandomCocktail() = viewModelScope.launch {
        _cocktails.value = listOf(randomCocktailUseCase())
    }

    fun searchCocktailsByIngredient(ingredient: String) = viewModelScope.launch {
        _cocktails.value = searchIngredientUseCase(ingredient)
    }

    fun fetchCategories() = viewModelScope.launch {
        _categories.value = getCategoriesUseCase()
    }
    fun filterCocktailsByCategory(categoryName: String) = viewModelScope.launch {
        _cocktails.value = filterCocktailsByCategories(categoryName)
    }

    // Clear the current cocktail list
    fun clearCocktails() {
        _cocktails.value = emptyList()
    }
}

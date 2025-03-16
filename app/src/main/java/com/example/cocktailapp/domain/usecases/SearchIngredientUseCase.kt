package com.example.cocktailapp.domain.usecases

import Cocktail
import com.example.cocktailapp.data.remote.repository.CocktailRepository
import javax.inject.Inject

class SearchIngredientUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(ingredient: String): List<Cocktail> {
        return repository.searchCocktailsByIngredient(ingredient)
    }
}

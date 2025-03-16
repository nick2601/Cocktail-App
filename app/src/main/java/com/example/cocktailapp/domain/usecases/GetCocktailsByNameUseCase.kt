package com.example.cocktailapp.domain.usecases


import Cocktail
import com.example.cocktailapp.data.remote.repository.CocktailRepository
import javax.inject.Inject

class GetCocktailsByNameUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(name: String): List<Cocktail> {
        return repository.getCocktailsByName(name)
    }
}

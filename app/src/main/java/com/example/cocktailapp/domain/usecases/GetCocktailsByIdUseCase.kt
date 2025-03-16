package com.example.cocktailapp.domain.usecases


import Cocktail
import com.example.cocktailapp.data.remote.repository.CocktailRepository
import javax.inject.Inject

class GetCocktailByIdUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(id: String): Cocktail? {
        return repository.getCocktailById(id)
    }
}

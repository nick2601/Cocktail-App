package com.example.cocktailapp.domain.usecases


import com.example.cocktailapp.data.remote.models.Category
import com.example.cocktailapp.data.remote.repository.CocktailRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
}

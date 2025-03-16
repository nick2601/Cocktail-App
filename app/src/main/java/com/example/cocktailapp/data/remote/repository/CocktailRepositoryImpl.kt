package com.example.cocktailapp.data.remote.repository

import Cocktail
import com.example.cocktailapp.data.remote.api.CocktailApiService
import com.example.cocktailapp.data.remote.models.Category
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val apiService: CocktailApiService
) : CocktailRepository {

    override suspend fun getCocktailsByName(name: String): List<Cocktail> = try {
            val response = apiService.searchCocktailByName(name)
            response.drinks ?: emptyList() // If 'drinks' is null, fallback to empty
        } catch (e: Exception) {
            // Log or handle error more thoroughly if needed
            emptyList()
        }

    override suspend fun getCocktailById(id: String): Cocktail? =
        apiService.getCocktailById(id).drinks?.firstOrNull()

    override suspend fun getRandomCocktail(): Cocktail =
        apiService.getRandomCocktail().drinks?.firstOrNull()
            ?: throw Exception("No cocktail found")

    override suspend fun getCategories(): List<Category> =
        apiService.getCategories().drinks

    override suspend fun searchCocktailsByIngredient(ingredient: String): List<Cocktail> =
        apiService.searchCocktailsByIngredient(ingredient).drinks ?: emptyList()

    override suspend fun filterCocktailsByCategory(categoryName: String): List<Cocktail> {
      return apiService.filterCocktailsByCategory(categoryName).drinks ?: emptyList()
    }
}

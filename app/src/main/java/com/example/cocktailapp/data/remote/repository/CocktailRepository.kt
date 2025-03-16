package com.example.cocktailapp.data.remote.repository

import Cocktail
import com.example.cocktailapp.data.remote.models.Category

interface CocktailRepository {
    suspend fun getCocktailsByName(name: String): List<Cocktail>
    suspend fun getCocktailById(id: String): Cocktail?
    suspend fun getCategories(): List<Category>
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchCocktailsByIngredient(ingredient: String): List<Cocktail>
    suspend fun filterCocktailsByCategory(categoryName: String): List<Cocktail>
}

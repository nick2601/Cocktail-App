package com.example.cocktailapp.data.remote.api



import CocktailResponse
import com.example.cocktailapp.data.remote.models.CategoryResponse
import com.example.cocktailapp.data.remote.models.IngredientResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    @GET("search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): CocktailResponse

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: String): CocktailResponse

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("list.php")
    suspend fun getCategories(@Query("c") list: String = "list"): CategoryResponse

    @GET("filter.php")
    suspend fun searchCocktailsByIngredient(@Query("i") ingredient: String): CocktailResponse

    @GET("filter.php")
    suspend fun filterCocktailsByCategory(@Query("c") category: String): CocktailResponse

    @GET("search.php")
    suspend fun searchIngredient(@Query("i") ingredient: String): IngredientResponse
}

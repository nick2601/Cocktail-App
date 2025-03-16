package com.example.cocktailapp.app.ui.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktailapp.app.ui.fragments.CategoriesScreen
import com.example.cocktailapp.app.ui.screens.CocktailDetailScreen
import com.example.cocktailapp.app.ui.screens.IngredientSearchScreen
import com.example.cocktailapp.app.ui.screens.SearchScreen

@Composable
fun CocktailNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("search") { SearchScreen(navController) }
        composable("ingredient_search") { IngredientSearchScreen(navController) }
        composable("categories") { CategoriesScreen(navController) }
        composable("detail/{cocktailId}") {
            val cocktailId = it.arguments?.getString("cocktailId") ?: ""
            CocktailDetailScreen(cocktailId = cocktailId)
        }
    }
}
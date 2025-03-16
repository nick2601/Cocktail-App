package com.example.cocktailapp.app.ui.screens

import Cocktail
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cocktailapp.app.ui.viewmodels.CocktailViewModel

@Composable
fun CocktailDetailScreen(
    cocktailId: String,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val cocktail = viewModel.selectedCocktail.collectAsState().value

    LaunchedEffect(cocktailId) {
        viewModel.fetchCocktailById(cocktailId)
    }

    if (cocktail == null) {
        // Loading or placeholder view
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        CocktailDetailContent(cocktail)
    }
}

@Composable
fun CocktailDetailContent(cocktail: Cocktail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Drink Image
        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            modifier = Modifier
                .size(250.dp)
                .padding(bottom = 16.dp)
        )

        // Drink Name
        Text(
            text = cocktail.strDrink,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Instructions
        if (!cocktail.strInstructions.isNullOrBlank()) {
            Text(
                text = "Instructions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = cocktail.strInstructions,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Ingredients
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val ingredientList = cocktail.ingredients.orEmpty()
        if (ingredientList.isEmpty()) {
            Text(
                text = "No ingredients found.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            ingredientList.forEach { ingredient ->
                Text(
                    text = "• $ingredient",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

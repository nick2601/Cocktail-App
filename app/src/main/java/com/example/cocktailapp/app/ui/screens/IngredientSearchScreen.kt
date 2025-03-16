package com.example.cocktailapp.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cocktailapp.app.ui.components.CocktailItem
import com.example.cocktailapp.app.ui.viewmodels.CocktailViewModel

@Composable
fun IngredientSearchScreen(
    navController: NavHostController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    var ingredientQuery by remember { mutableStateOf("") }
    val cocktails by viewModel.cocktails.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = ingredientQuery,
            onValueChange = { ingredientQuery = it },
            label = { Text(text = "Search by Ingredient") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { viewModel.searchCocktailsByIngredient(ingredientQuery) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Search")
            }

            OutlinedButton(
                onClick = { ingredientQuery = ""; viewModel.clearCocktails() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Clear")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(cocktails.filter { it.strDrink.contains(ingredientQuery, ignoreCase = true) }) { cocktail ->
                CocktailItem(cocktail = cocktail, onItemClick = {
                    navController.navigate("detail/${cocktail.idDrink}")
                })
            }
        }
    }
}
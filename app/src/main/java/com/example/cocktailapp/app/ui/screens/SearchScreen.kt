package com.example.cocktailapp.app.ui.screens
import Cocktail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cocktailapp.app.ui.components.CocktailItem
import com.example.cocktailapp.app.ui.viewmodels.CocktailViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var cocktails by remember { mutableStateOf(emptyList<Cocktail>()) }

    // Collecting cocktails from ViewModel
    LaunchedEffect(Unit) {
        viewModel.cocktails.collectLatest { fetchedCocktails ->
            cocktails = fetchedCocktails
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Cocktails") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.fetchCocktailsByName(searchQuery) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(cocktails.size, key = { cocktails[it].idDrink}) { cocktail ->
                CocktailItem(cocktail = cocktails[cocktail]) {
                    navController.navigate("detail/${cocktails[cocktail].idDrink}")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cocktails.collectLatest { fetchedCocktails ->
            cocktails = fetchedCocktails
        }
    }
}

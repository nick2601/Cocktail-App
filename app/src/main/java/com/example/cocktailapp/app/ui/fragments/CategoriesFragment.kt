package com.example.cocktailapp.app.ui.fragments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cocktailapp.app.ui.viewmodels.CocktailViewModel

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(categories) { category ->
            Text(
                text = category.strCategory,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("category/\${category.strCategory}")
                    }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cocktailapp.app.ui.components.CategoryDialog
import com.example.cocktailapp.app.ui.components.CocktailItem
import com.example.cocktailapp.app.ui.viewmodels.CocktailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    // -----------------------------
    // States & Collecting Data
    // -----------------------------
    val searchQuery = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    // Category selection states
    val isCategoryDialogOpen = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf("") }

    // Collect cocktails and categories from the ViewModel
    val cocktails by viewModel.cocktails.collectAsState(initial = emptyList())
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    // For random cocktails or large lists
    val lazyListState = rememberLazyListState()

    // -----------------------------
    // Initial Data Fetch
    // -----------------------------
    LaunchedEffect(Unit) {
        viewModel.fetchCocktailsByName("margarita") // Default query
    }

    // -----------------------------
    // Main Scaffold
    // -----------------------------
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cocktail App", style = MaterialTheme.typography.headlineSmall)
                },
                actions = {
                    // Info icon => fetch categories & open dialog
                    IconButton(onClick = {
                        viewModel.fetchCategories()
                        isCategoryDialogOpen.value = true
                    }) {
                        Icon(Icons.Default.Info, contentDescription = "Categories Info")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                errorMessage.value = ""
                viewModel.fetchRandomCocktail()
                // If user had selected a category, reset it to show random results
                selectedCategory.value = ""
            }) {
                Icon(Icons.Default.Refresh, contentDescription = "Random Cocktail")
            }
        }
    ) { innerPadding ->
        // -----------------------------
        // Content Column
        // -----------------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Search/Filter UI
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                    errorMessage.value = ""
                    // If user starts typing again, reset category
                    selectedCategory.value = ""
                },
                label = { Text("Search by Cocktail Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val query = searchQuery.value.trim()
                        if (query.isBlank()) {
                            errorMessage.value = "Please enter a valid cocktail name."
                        } else {
                            errorMessage.value = ""
                            selectedCategory.value = ""
                            viewModel.fetchCocktailsByName(query)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Search")
                }

                OutlinedButton(
                    onClick = {
                        searchQuery.value = ""
                        errorMessage.value = ""
                        selectedCategory.value = ""
                        viewModel.clearCocktails()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Clear Search")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show error message if any
            if (errorMessage.value.isNotBlank()) {
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Decide final cocktails to display
            val finalCocktails = when {
                selectedCategory.value.isNotEmpty() -> {
                    // Show category-based cocktails if a category is selected
                    cocktails
                }
                else -> {
                    // Otherwise, apply local filter for name-based search
                    cocktails.filter {
                        it.strDrink.contains(searchQuery.value, ignoreCase = true)
                    }
                }
            }

            if (finalCocktails.isEmpty() && errorMessage.value.isEmpty()) {
                Text("No cocktails found.", style = MaterialTheme.typography.bodyMedium)
            } else {
                // Show the final list
                LazyColumn(state = lazyListState) {
                    items(finalCocktails, key = { it.idDrink }) { cocktail ->
                        CocktailItem(
                            cocktail = cocktail,
                            onItemClick = {
                                navController.navigate("detail/${cocktail.idDrink}")
                            }
                        )
                    }
                }
            }
        }
    }

    // -----------------------------
    // Category Dialog
    // -----------------------------
    if (isCategoryDialogOpen.value) {
        CategoryDialog(
            categories = categories,
            onDismissRequest = { isCategoryDialogOpen.value = false },
            onCategorySelected = { category ->
                isCategoryDialogOpen.value = false
                selectedCategory.value = category
                // Fetch cocktails for that category from the API
                viewModel.filterCocktailsByCategory(category)
            }
        )
    }
}

/**
 * Simple local filter function for name-based searching,
 * used only when no category is selected.
 */
fun filterCocktails(cocktails: List<Cocktail>, query: String): List<Cocktail> {
    return cocktails.filter { it.strDrink.contains(query, ignoreCase = true) }
}

/**
 * Composable function to display a list of cocktails.
 *
 * @param cocktails The list of cocktails to display.
 * @param navController The NavHostController for navigation.
 * @param lazyListState State for the LazyColumn
 */
@Composable
fun CocktailList(
    cocktails: List<Cocktail>,
    navController: NavHostController,
    lazyListState: LazyListState
) {
    LazyColumn(state = lazyListState) {
        items(cocktails, key = { it.idDrink }) { cocktail ->
            CocktailItem(
                cocktail = cocktail,
                onItemClick = {
                    navController.navigate("detail/${cocktail.idDrink}")
                }
            )
        }
    }
}

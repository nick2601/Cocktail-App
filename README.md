# CocktailApp

Welcome to **CocktailApp**, a modern Android application built with **Jetpack Compose**, **MVVM**, and **Clean Architecture**. The app fetches data from [TheCocktailDB API](https://www.thecocktaildb.com/) to provide a wide range of cocktail information, such as cocktail details, searching by name, filtering by category, ingredient-based searches, and random cocktails.

---

## Table of Contents
1. [Architecture Overview](#architecture-overview)
2. [Project Layers](#project-layers)
    1. [Domain Layer](#domain-layer)
    2. [Data Layer](#data-layer)
    3. [Presentation Layer (UI)](#presentation-layer)
3. [API Endpoints & Usage](#api-endpoints--usage)
4. [User Journey](#user-journey)
5. [Additional Features & Notes](#additional-features--notes)

---

## Architecture Overview

**CocktailApp** follows **Clean Architecture** with **MVVM**:

- **Domain**: Contains business logic and use cases.
- **Data**: Handles repositories and remote/local data sources.
- **Presentation (UI)**: Jetpack Compose screens and ViewModels that observe state flows.

This structure ensures **scalability**, **testability**, and **maintainability**.

---

## Project Layers

### Domain Layer
- **Purpose**: Encapsulates business logic in use cases and domain models.
- **Key Components**:
    - **Use Cases**: E.g., `GetCocktailsByNameUseCase`, `GetCocktailByIdUseCase`, `SearchIngredientUseCase`, etc.
    - **Models**: Domain versions of `Cocktail`, `Category`, or `Ingredient` if needed.

**Example**: `GetCocktailsByNameUseCase` calls the repository to retrieve cocktails from TheCocktailDB, returning a list of domain `Cocktail` objects.

### Data Layer
- **Purpose**: Implements repositories and data models, bridging remote or local sources to domain.
- **Key Components**:
    - **Remote** (Retrofit API): `CocktailApiService` defines endpoints like `search.php?s=...`, `list.php?c=list`, etc.
    - **Repository**: E.g., `CocktailRepositoryImpl` which queries the `CocktailApiService` and safely handles null or invalid JSON.
    - **Models**: Data classes like `CocktailResponse`, `IngredientResponse`, etc., matching TheCocktailDB JSON.

**Example**: `CocktailRepositoryImpl` uses `searchCocktailByName(name)` to fetch JSON from TheCocktailDB, converting it into domain-friendly data.

### Presentation Layer
- **Purpose**: Displays data with **Jetpack Compose** and handles user interaction via **ViewModel**.
- **Key Components**:
    - **ViewModel** (e.g., `CocktailViewModel`): Manages state flows (`StateFlow`) for cocktails, categories, or errors. Calls use cases in `viewModelScope.launch`.
    - **Screens (Jetpack Compose)**:
        - **HomeScreen**: Main entry screen with search, random cocktail, and category dialog.
        - **IngredientSearchScreen**: Allows filtering by ingredient.
        - **CategoriesScreen**: Shows cocktails belonging to a specific category.
        - **CocktailDetailScreen**: Displays detailed info: instructions, ingredients, and images.

**Example**: `HomeScreen` collects `cocktails` from the ViewModel, filters them by user input, and navigates to `CocktailDetailScreen` on item tap.

---

## API Endpoints & Usage
The app integrates with **[TheCocktailDB](https://www.thecocktaildb.com/api.php)** using these major endpoints:

1. **Search by Name**: `search.php?s=Margarita`
    - Fetch cocktails matching a given name.

2. **Filter by Ingredient**: `filter.php?i=Gin`
    - Returns cocktails containing a specified ingredient.

3. **Lookup by Cocktail ID**: `lookup.php?i=11007`
    - Retrieve detailed info for a specific cocktail.

4. **List Categories**: `list.php?c=list`
    - Provides available cocktail categories (Ordinary Drink, Cocktail, etc.).

5. **Filter by Category**: `filter.php?c=Ordinary_Drink`
    - Returns cocktails in a specific category.

6. **Random Cocktail**: `random.php`
    - Retrieves a single random cocktail.

7. **Search Ingredient**: `search.php?i=Vodka`
    - Detailed info for a specific ingredient (optional usage).

**Error Handling**:
- The repository checks for null or invalid JSON fields, returning safe defaults to avoid crashes.

---

## User Journey

1. **Home Screen**
    - **Top Bar**: Title “Cocktail App” and info button (displays category dialog).
    - **Search**: Outlined text field to search by name, with a “Search” button.
    - **Clear**: Resets the search query and results.
    - **Random FAB**: Fetches a random cocktail from TheCocktailDB.
    - **Category Dialog**: On tapping info, user sees a list of categories. Selecting one fetches category cocktails.
    - Displays a lazy list of cocktails; tapping a cocktail navigates to **CocktailDetailScreen**.

2. **Cocktail Detail Screen**
    - Shows an image, name, instructions, and ingredients.
    - If data is not yet loaded, displays a `CircularProgressIndicator`.

3. **Ingredient Search Screen** (Optional)
    - Users can search by a given ingredient. The UI calls `filter.php?i=<ingredient>`, listing results.

4. **Categories Screen** (Optional)
    - Displays cocktails for a chosen category if you prefer a dedicated screen.

**Notes**:
- The app gracefully handles no-result queries by showing “No cocktails found”.
- If the user provides an empty search query, displays an error message.

---

## Additional Features & Notes

- **Hilt for Dependency Injection**: Simplifies creation of `CocktailRepository`, `Retrofit`, etc.
- **Coroutines & StateFlow**: All network calls done in `viewModelScope.launch`, exposing a `StateFlow` to composables.
- **Material 3** theming**: The UI leverages `MaterialTheme.typography` & color scheme for a cohesive look.
- **Navigation**: Single activity with Jetpack Compose Navigation. Each screen is a composable route.
- **Scaling**: Extend to favorites or offline caching if desired by implementing a local database (Room) and storing cocktails.
- **Error Handling**: Surfaces messages in the UI (e.g., “Please enter valid input”) to maintain user awareness.


---

### Thank you for exploring CocktailApp!
**Enjoy** building and customizing the experience for your users.

If you have any questions or issues, feel free to reach out or open a pull request. Happy coding!


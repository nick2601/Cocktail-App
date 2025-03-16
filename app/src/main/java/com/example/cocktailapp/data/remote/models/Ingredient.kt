package com.example.cocktailapp.data.remote.models

data class IngredientResponse(val ingredients: List<Ingredient>)

data class Ingredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?
)

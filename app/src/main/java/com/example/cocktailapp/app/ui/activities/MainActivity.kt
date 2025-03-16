package com.example.cocktailapp.app.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cocktailapp.app.ui.navigation.CocktailNavGraph
import com.example.cocktailapp.ui.theme.CocktailAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailAppTheme {
                CocktailNavGraph()
            }
        }
    }
}

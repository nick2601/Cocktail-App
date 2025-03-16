package com.example.cocktailapp.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.data.remote.models.Category

@Composable
fun CategoryDialog(
    categories: List<Category>,
    onDismissRequest: () -> Unit,
    onCategorySelected: (String) -> Unit
) {
    val onCategorySelect: (String) -> Unit = { category ->
        onCategorySelected(category)
    }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select a Category") },
        text = {
            if (categories.isEmpty()) {
                CircularProgressIndicator()
            } else {
                Column {
                    categories.forEach { cat ->
                        Text(
                            text = cat.strCategory,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    onCategorySelect(cat.strCategory)
                                }
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            OutlinedButton(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}

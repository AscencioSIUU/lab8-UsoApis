package com.example.lab8_apisaplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

// Main CategoryScreen that displays the categories and handles navigation
@Composable
fun CategoryScreen(viewModel: MainViewModel = viewModel(), navController: NavController) {
    val categoriesState by viewModel.categoriesState

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            categoriesState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            categoriesState.error != null -> {
                Text(text = "Error: ${categoriesState.error}")
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(categoriesState.list) { category ->
                        CategoryItem(category = category) {
                            // Navigate to the MealsScreen when clicking on a category
                            navController.navigate("meals/${category.strCategory}")
                        }
                    }
                }
            }
        }
    }
}

// Composable for displaying a single category item
@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // OnClick navigates to the next screen
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Load category thumbnail using Coil
        Image(
            painter = rememberImagePainter(data = category.strCategoryThumb),
            contentDescription = category.strCategory,
            modifier = Modifier.size(80.dp), // Image size
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

        Text(text = category.strCategory, style = MaterialTheme.typography.bodyMedium)
    }
}

// Preview for testing CategoryItem in isolation
@Preview(showBackground = true)
@Composable
fun PreviewCategoryItem() {
    CategoryItem(
        category = Category(
            idCategory = "1",
            strCategory = "Seafood",
            strCategoryThumb = "https://www.themealdb.com/images/category/seafood.png",
            strCategoryDescription = "Seafood includes fish and shellfish..."
        ),
        onClick = {}
    )
}
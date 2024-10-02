package com.example.lab8_apisaplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text


/**
@Composable
fun CategoryListScreen(
    viewModel: List<Category>,
    onCategoryClick: (String) -> Unit // Callback que pasará el nombre de la categoría
) {
    val categoriesState by viewModel.categoriesState
    if (categoriesState.loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else if (categoriesState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error: ${categoriesState.error}")
        }
    }
    else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(categoriesState.list) { category ->
                CategoryItem(category = category, onClick = { onCategoryClick(category.strCategory) })
            }
        }
    }
}
**/

@Composable
fun CategoryListScreen(categories: List<Category>, onCategoryClick: (String) -> Unit) {
    LazyColumn {
        items(categories) { category ->
            Text(
                text = category.strCategory,
                modifier = Modifier
                    .clickable { onCategoryClick(category.strCategory) }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Al hacer clic, llamamos a la función onClick
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cargar la imagen con Coil
        Image(
            painter = rememberImagePainter(data = category.strCategoryThumb),
            contentDescription = category.strCategory,
            modifier = Modifier.size(80.dp), // Tamaño de la imagen
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp)) // Espacio entre la imagen y el texto

        Text(text = category.strCategory, style = MaterialTheme.typography.bodyMedium)
    }
}

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

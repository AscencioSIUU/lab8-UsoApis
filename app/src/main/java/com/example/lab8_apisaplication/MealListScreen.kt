package com.example.lab8_apisaplication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MealListScreen(meals: List<Meal>, onMealClick: (String) -> Unit) {
    LazyColumn {
        items(meals) { meal ->
            Text(
                text = meal.strMeal,
                modifier = Modifier
                    .clickable { onMealClick(meal.idMeal) }
                    .padding(16.dp)
            )
        }
    }
}

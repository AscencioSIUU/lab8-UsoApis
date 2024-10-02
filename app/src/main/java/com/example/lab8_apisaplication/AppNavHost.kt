package com.example.lab8_apisaplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home"){
            HomeScreen(viewModel)
        }
        composable("categories") {
            CategoryListScreen(viewModel.categories.value) { category ->
                viewModel.fetchMealsByCategory(category)
                navController.navigate("meals/$category")
            }
        }
        composable("meals/{category}") { backStackEntry ->
            MealListScreen(viewModel.meals.value) { mealId ->
                viewModel.fetchMealDetails(mealId)
                navController.navigate("recipe/$mealId")
            }
        }
        composable("recipe/{mealId}") {
            viewModel.mealDetails.value?.let { it1 -> RecipeScreen(it1) }
        }
    }
}


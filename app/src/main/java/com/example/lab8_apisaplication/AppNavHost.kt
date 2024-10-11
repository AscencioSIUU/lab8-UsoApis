package com.example.lab8_apisaplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = "categories") {
        composable("categories") {
            CategoryScreen(viewModel = viewModel, navController = navController)
        }
        composable("meals/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            category?.let {
                viewModel.fetchMealsByCategory(it)
                MealListScreen(viewModel.meals.value) { mealId ->
                    viewModel.fetchMealDetails(mealId)
                    // Navigate to recipe screen and clear the stack
                    navController.navigate("recipe/$mealId") {
                        popUpTo("categories") { inclusive = true }
                    }
                }
            }
        }
        composable("recipe/{mealId}") { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")
            mealId?.let {
                viewModel.mealDetails.value?.let { mealDetails ->
                    RecipeScreen(mealDetails)
                }
            }
        }
    }
}

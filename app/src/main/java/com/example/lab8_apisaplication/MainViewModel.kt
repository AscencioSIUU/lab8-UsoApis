package com.example.lab8_apisaplication

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    // Estado para la lista de categorías
    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    // Inicializa el servicio de API
    private val apiService: ApiService

    init {
        // Inicializa Retrofit y el servicio API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Llamar al método fetchCategories después de inicializar apiService
        fetchCategories()
    }

    // Método para obtener las categorías
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = apiService.getCategories() // Llamada a la API
                _categoriesState.value = _categoriesState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error Fetching Categories ${e.message}"
                )
                Log.e("NetworkError", "Error Fetching Categories", e)
            }
        }
    }

    // Estado de la receta
    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

    // Otras funciones para obtener comidas y detalles de comidas
    val categories = mutableStateOf<List<Category>>(emptyList())
    val meals = mutableStateOf<List<Meal>>(emptyList())
    val mealDetails = mutableStateOf<MealDetails?>(null)

    // Método para obtener comidas por categoría
    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            meals.value = apiService.getMealsByCategory(category).meals
        }
    }

    // Método para obtener detalles de una comida por ID
    fun fetchMealDetails(id: String) {
        viewModelScope.launch {
            val details = apiService.getMealDetails(id).meals.firstOrNull()
            mealDetails.value = details
        }
    }
}

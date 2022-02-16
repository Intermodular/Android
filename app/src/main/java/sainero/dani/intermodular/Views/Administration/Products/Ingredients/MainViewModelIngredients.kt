package sainero.dani.intermodular.Views.Administration.Products.Ingredients

import androidx.lifecycle.ViewModel

class MainViewModelIngredients: ViewModel() {
    var _ingredients: MutableList<String> = mutableListOf()
    var ingredientsState = "New"

    var _tmpIngredients: MutableList<String> = mutableListOf()
    var newsValuesIngredients: MutableList<String> = mutableListOf()

    fun addTmpIngredients(newValue: String) {
        _tmpIngredients.add(newValue)
    }

    fun addIngredient(newValue: String) {
        _ingredients.add(newValue)
    }

    fun getIngredients() : MutableList<String> {
        return _ingredients
    }
}
package sainero.dani.intermodular.Views.Administration.Products.Ingredients

import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class MainViewModelIngredients: ViewModel() {
    var _ingredients: MutableList<String> = mutableListOf()
    var ingredientsState = "New"

    var tmpIngredients: MutableList<String> = mutableListOf()
    var newsValuesIngredients: MutableList<String> = mutableListOf()

    fun addIngredient(newValue: String) {
        _ingredients.add(newValue)
    }


    //Validaciones
    private fun isValidIngredient(text: String) = Pattern.compile("^[a-zA-Z]{1,14}$", Pattern.CASE_INSENSITIVE).matcher(text).find()

}
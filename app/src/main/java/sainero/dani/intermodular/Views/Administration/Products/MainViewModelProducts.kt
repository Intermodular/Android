package sainero.dani.intermodular.Views.Administration.Products

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceProduct
import sainero.dani.intermodular.Api.ApiServiceType
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Tipos
import java.util.regex.Pattern

class MainViewModelProducts: ViewModel() {

    //Consultas BD
    private var errorMessage: String by mutableStateOf ("")

    //Métodos get
    var productListResponse: List <Productos> by mutableStateOf ( listOf ())
    fun getProductList() {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

            try {
                val productList = apiService.getProducts()

                productListResponse = productList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var product: Productos by mutableStateOf (Productos(0,"","", arrayListOf("") ,2f, arrayListOf(""),"",0))
    fun getProductById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

            try {
                val respuesta = apiService.getProductById(id)
                if (respuesta.isSuccessful)
                    product = respuesta.body()!!
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var typeListResponse: List <Tipos> by mutableStateOf ( listOf ())
    fun getTypesList() {
        viewModelScope.launch {
            val apiService = ApiServiceType.getInstance()

            try {
                val typeList = apiService.getTypes()
                typeListResponse = typeList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newProduct: Productos by mutableStateOf (Productos(0,"","", arrayListOf("") ,2f, arrayListOf(""),"",0))
    fun uploadProduct(product: Productos) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

            try {
                val result = apiService.uploadProduct(product)
                if (result.isSuccessful)
                    newProduct = result.body()!!
                else
                    Log.d("Error: upload product","Error: upload product")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos put
    var editProduct: Productos by mutableStateOf (Productos(0,"","", arrayListOf("") ,2f, arrayListOf(""),"",0))
    fun editProduct(product: Productos) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()
            try {
                val result = apiService.editProduct(product = product)
                if (result.isSuccessful)
                    editProduct = result.body()!!
                else
                    Log.d("Error: edit product","Error: edit product")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteProduct: Productos by mutableStateOf (Productos(0,"","", arrayListOf("") ,2f, arrayListOf(""),"",0))

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

            try {
                val result = apiService.deleteProduct(id)
                if (result.isSuccessful)
                    deleteProduct = result.body()!!
                else
                    Log.d("Error: edit product","Error: edit product")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Validaciones
    fun isValidNameOfProduct(text: String) = Pattern.compile("^[a-zA-Z ]{1,20}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidCostOfProduct(text: String) = Pattern.compile("^[0-9.]{1,20}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidStockOfProduct(text: String) = Pattern.compile("^[0-9]{1,20}$", Pattern.CASE_INSENSITIVE).matcher(text).find()

    fun checkAllValidations(
        textName: String,
        textPrice: String,
        textStock:String,
    ): Boolean {
        if (
            !isValidNameOfProduct(text = textName) ||
            !isValidCostOfProduct(text = textPrice) ||
            !isValidStockOfProduct(text = textStock)
        )  return false

        return  true
    }

}
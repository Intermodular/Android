package sainero.dani.intermodular.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiService
import sainero.dani.intermodular.DataClass.Productos

class ViewModelProductos: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var productListResponse: List <Productos> by mutableStateOf ( listOf ())
    fun getProductList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val productList = apiService.getproducts()
                productListResponse = productList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var product: List <Productos> by mutableStateOf(listOf())

    fun getProductById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val productById = apiService.getProductById(id)
                product = productById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    fun uploadProduct(product: Productos) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.uploadProduct(product)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun editProduct(product: Productos) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                apiService.editProduct(product._id)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.deleteProduct(id)


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
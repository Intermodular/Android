package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceProduct
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Users

class ViewModelProductos: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var productListResponse: List <Productos> by mutableStateOf ( listOf ())
    fun getProductList() {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

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
            val apiService = ApiServiceProduct.getInstance()

            try {
                val productById = apiService.getProductById(id)
                product = productById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    val newProduct: MutableList<Productos> = mutableListOf()
    fun uploadProduct(product: Productos) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

            try {
                val result = apiService.uploadProduct(product)
                if (result.isSuccessful)
                     newProduct.add(result.body()!!)
                else
                    Log.d("Error: upload product","Error: upload product")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    val editProduct: MutableList<Productos> = mutableListOf()
    fun editProduct(product: Productos) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()
            try {
                val result = apiService.editProduct(product = product)
                if (result.isSuccessful)
                    editProduct.add(result.body()!!)
                else
                    Log.d("Error: edit product","Error: edit product")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    val deleteProduct: MutableList<Productos> = mutableListOf()

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceProduct.getInstance()

            try {
                val result = apiService.deleteProduct(id)
                if (result.isSuccessful)
                    deleteProduct.add(result.body()!!)
                else
                    Log.d("Error: edit product","Error: edit product")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
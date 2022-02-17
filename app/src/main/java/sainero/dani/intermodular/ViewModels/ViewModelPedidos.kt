package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceOrder
import sainero.dani.intermodular.DataClass.Pedidos

class ViewModelPedidos: ViewModel() {

    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var orderListResponse: List <Pedidos> by mutableStateOf ( listOf ())
    fun getOrderList() {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()

            try {
                val respuesta = apiService.getOrders()
                orderListResponse = respuesta

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var order: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun getOrderById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()

            try {
                val respuesta = apiService.getOrderById(id)
                if (respuesta.isSuccessful)
                    order = respuesta.body()!!
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun uploadOrder(order: Pedidos) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()

            try {
                val result = apiService.uploadOrder(order)
                if (result.isSuccessful)
                    newOrder = result.body()!!
                else
                    Log.d("Error: upload order","Error: upload order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Put
    var editOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun editOrder(order: Pedidos) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.editOrder(order = order)
                if (result.isSuccessful)
                    editOrder = result.body()!!
                else
                    Log.d("Error: edit order","Error: edit order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))

    fun deleteOrder(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()

            try {
                val result = apiService.deleteOrder(id)
                if (result.isSuccessful)
                    deleteOrder = result.body()!!
                else
                    Log.d("Error: edit order","Error: edit order")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
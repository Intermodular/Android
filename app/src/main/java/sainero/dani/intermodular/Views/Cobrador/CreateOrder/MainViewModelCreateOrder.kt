package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceOrder
import sainero.dani.intermodular.Api.ApiServiceTable
import sainero.dani.intermodular.Api.ApiServiceZone
import sainero.dani.intermodular.DataClass.*
import java.lang.NumberFormatException

class MainViewModelCreateOrder : ViewModel() {

    //Base de datos
    private var errorMessage: String by mutableStateOf ("")

    //Métodos get
    var orderByTable: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun getOrderByTable(id: Int, onValueFinish: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.getOrderByTable(id)
                if (result.isSuccessful) {
                    orderByTable = result.body()!!
                    onValueFinish()
                }
                else
                    Log.d("Error: upload order","Error: upload order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var mesasListResponse: List <Mesas> by mutableStateOf ( listOf ())
    fun getMesaList(onValueFinish: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {
                val result = apiService.getTables()
                if (result.isSuccessful) {
                    mesasListResponse = result.body()!!
                    onValueFinish()
                }
                else {
                    Log.d("Error to get table","Error to get table")
                }

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getOrderByTableWithDelay(id: Int, onValueFinish: (Boolean) -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.getOrderByTable(id)
                if (result.isSuccessful){
                    orderByTable = result.body()!!
                    onValueFinish(true)
                }
                else{
                    onValueFinish(false)
                    Log.d("Error: upload order","Error: upload order")
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }


    //Métodos post
    var newOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun uploadOrder(order: Pedidos, onValueFinish: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()

            try {
                val result = apiService.uploadOrder(order)
                val x = result.body()!!
                if (result.isSuccessful) {
                    newOrder = result.body()!!
                    onValueFinish()
                }
                else
                    Log.d("Error: upload order","Error: upload order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos put
    var valueOfEditOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun editOrder(order: Pedidos, onValueFinish: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.editOrder(order = order)
                if (result.isSuccessful){
                    valueOfEditOrder = result.body()!!
                    onValueFinish()
                }
                else
                    Log.d("Error: edit order","Error: edit order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))

    fun deleteOrder(id: Int,idMesa: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.deleteOrder(id = id, idMesa = idMesa)
                if (result.isSuccessful) {
                    deleteOrder = result.body()!!
                }
                else
                    Log.d("Error: edit order","Error: edit order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }


    //Variables
    var pedido: Pedidos = Pedidos(0,0, arrayListOf())
    var lineasPedidos: MutableList<LineaPedido> = mutableListOf()
    var lineasExtras: MutableList<LineaExtra> = mutableListOf()
    var editOrder = true
    var editLineOrder: LineaPedido = LineaPedido(Productos(0,"","", arrayListOf(),0f, arrayListOf(),"",0),0,"", arrayListOf(),0f,)
    var editLineOrderIndex: Int = 0


    //Validaciones
    fun isInteger(
        text:String
    ): Boolean {
        try {
            text.toInt()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }


    fun calculateLinePrice(
        mainViewModelCreateOrder: MainViewModelCreateOrder,
        product: Productos,
        textQuantity: Int
    ): Float {
        var priceTotalExtras = 0f
        mainViewModelCreateOrder.lineasExtras.forEach{ priceTotalExtras += (it.extra.price * it.cantidad)}
        var linePrice: Float = (product.price + priceTotalExtras) * textQuantity
        return linePrice
    }




}
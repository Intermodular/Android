package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceOrder
import sainero.dani.intermodular.DataClass.LineaExtra
import sainero.dani.intermodular.DataClass.LineaPedido
import sainero.dani.intermodular.DataClass.Pedidos
import sainero.dani.intermodular.DataClass.Productos

class MainViewModelCreateOrder : ViewModel() {

    //Base de datos
    private var errorMessage: String by mutableStateOf ("")

    //Métodos get
    var orderByTable: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun getOrderByTable(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.getOrderByTable(id)
                if (result.isSuccessful)
                    orderByTable = result.body()!!
                else
                    Log.d("Error: upload order","Error: upload order")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }

    }//Métodos get
    fun getOrderByTableWithDelay(id: Int, onValueFinish: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.getOrderByTable(id)
                if (result.isSuccessful){
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

    //Métodos put
    var getEditOrder: Pedidos by mutableStateOf (Pedidos(_id = 0,idMesa = 0,lineasPedido = arrayListOf()))
    fun editOrder(order: Pedidos) {
        viewModelScope.launch {
            val apiService = ApiServiceOrder.getInstance()
            try {
                val result = apiService.editOrder(order = order)
                if (result.isSuccessful)
                    getEditOrder = result.body()!!
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













    var clearAllVariables = true

    var getOrder = true
    private val _lineaPedido: LineaPedido = LineaPedido(lineasExtra = arrayListOf(),costeLinea = 0f,cantidad = 0,anotaciones = "",producto = Productos(_id = 0,type = "",price = 0f,especifications = arrayListOf(),stock = 0,img = "",ingredients = arrayListOf(),name = ""))


    private val _lineaExtras = ""
    var pedidoEditar: LineaPedido = LineaPedido(lineasExtra = arrayListOf(),costeLinea = 0f,cantidad = 0,anotaciones = "",producto = Productos(_id = 0,type = "",price = 0f,especifications = arrayListOf(),stock = 0,img = "",ingredients = arrayListOf(),name = ""))

    fun addLineaPedido(newValue: LineaPedido) {
        lineasPedidos.add(newValue)
    }

    fun addLineaExtras(newValue: LineaExtra) {
        lineasExtras.add(newValue)
    }




}
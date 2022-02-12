package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import sainero.dani.intermodular.DataClass.LineaExtra
import sainero.dani.intermodular.DataClass.LineaPedido
import sainero.dani.intermodular.DataClass.Pedidos
import sainero.dani.intermodular.DataClass.Productos

class MainViewModelCreateOrder : ViewModel() {
/*
    private val _searchWidgetState: MutableState<CreateOrderWidgetState> = mutableStateOf(value = CreateOrderWidgetState.CLOSED)
    val searchWidgetState: State<CreateOrderWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: CreateOrderWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
*/

    private val _lineaPedido: LineaPedido = LineaPedido(lineasExtra = arrayListOf(),costeLinea = 0f,cantidad = 0,anotaciones = "",producto = Productos(_id = 0,type = "",price = 0f,especifications = arrayListOf(),stock = 0,img = "",ingredients = arrayListOf(),name = ""))
    val _lineasPedidos: MutableList<LineaPedido> = mutableListOf()
    private val _lineaExtras = ""
    val _lineasExtras: MutableList<LineaExtra> = mutableListOf()

    fun addLineaPedido(newValue: LineaPedido) {
        _lineasPedidos.add(newValue)
    }

    fun addLineaExtras(newValue: LineaExtra) {
        _lineasExtras.add(newValue)
    }

}
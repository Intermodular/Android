package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sainero.dani.intermodular.DataClass.LineaPedido
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Pedidos
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelPedidos
import sainero.dani.intermodular.ViewsItems.confirmAlertDialog

class EditOrder : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainEditOrder(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    tableId: Int,
    viemModelPedidos: ViewModelPedidos,
    viewModelMesas: ViewModelMesas


) {
    //Variables
    var (refresh, onValueChangeRefresh) = rememberSaveable { mutableStateOf(true) }


    Scaffold(

        content = {
            if (refresh) CreateContent(mainViewModelCreateOrder = mainViewModelCreateOrder, onValueChangeRefresh = onValueChangeRefresh, tableId = tableId, viemModelPedidos = viemModelPedidos, viewModelMesas = viewModelMesas)

        }
    )


}

@Composable
private fun CreateContent(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    tableId: Int,
    viemModelPedidos: ViewModelPedidos,
    viewModelMesas: ViewModelMesas,
    onValueChangeRefresh: (Boolean)-> Unit
    ){
    var selectedTable = remember{ Mesas(_id = 0,state = "Libre",number = 0,numChair = 0,zone = "")}
    viewModelMesas.mesasListResponse.forEach { if (it._id.equals(tableId)) selectedTable = it}
    var indexItem : MutableState<Int> = remember{ mutableStateOf(0)}
    var (deleteLane,onValueChangeDeleteLane) = remember { mutableStateOf(false) }
    var lineaPedidoSeleccionada = remember {
        mutableStateOf(LineaPedido(Productos(0,"","", arrayListOf(),0f,
            arrayListOf(),"",0),0,"", arrayListOf(),0f))}

    if (deleteLane) {
        var title: String = "¿Seguro que desea eliminar la linea de pedido seleccionada?"
        var subtitle: String = "Nombre " + lineaPedidoSeleccionada.value.producto.name + ". Cantidad: " + lineaPedidoSeleccionada.value.cantidad.toString()

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteLane,
        ) {
            if (it) {
                //mainViewModelCreateOrder.lineasPedidos.remove(lineaPedidoSeleccionada.value)

                mainViewModelCreateOrder.lineasPedidos.removeAt(indexItem.value)
                lineaPedidoSeleccionada.value = LineaPedido(Productos(0,"","", arrayListOf(),0f,
                    arrayListOf(),"",0),0,"", arrayListOf(),0f)
                //mainViewModelCreateOrder.lineasPedidos.removeAt(lineaPedidoSeleccionada)
                //onValueChangeRefresh(false)
                //onValueChangeRefresh(true)
                //lineaPedidoSeleccionada.value = (LineaPedido(Productos(0,"","", arrayListOf(),0f,arrayListOf(),"",0),0,"", arrayListOf(),0f))
                onValueChangeDeleteLane(false)
                //navController.popBackStack()
            }
        }
    }
    LazyColumn(
        content = {
            item {

                Row(
                    //verticalArrangement = Arrangement.SpaceAround,
                    //horizontalAlignment = Alignment.Start
                    //horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, bottom = 20.dp, end = 25.dp)
                        .clickable {

                        }
                ) {
                    Text(
                        text = "Nombre",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                    )
                    Text(
                        text = "Cantidad",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                    )
                    Text(
                        text = "Precio",
                        textAlign = TextAlign.Right,
                        modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                    )
                    //Spacer(modifier = Modifier.padding(start = 55.dp, bottom = 20.dp))
                }
            }
                itemsIndexed(mainViewModelCreateOrder.lineasPedidos) { index, it ->
                    Column(
                        //verticalArrangement = Arrangement.SpaceAround,
                        //horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 25.dp, bottom = 20.dp, end = 25.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { Offset ->
                                            lineaPedidoSeleccionada.value = mainViewModelCreateOrder.lineasPedidos.get(index)
                                            indexItem.value = index
                                            onValueChangeDeleteLane(true)
                                        },
                                        onTap = { Offset ->
                                            val idLineaPedido =
                                                mainViewModelCreateOrder.lineasPedidos.indexOf(
                                                    it
                                                )
                                        }
                                    )
                                }


                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = it.producto.name.toString(),
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = it.cantidad.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                                )
                            }
                            //Spacer(modifier = Modifier.padding(start = 50.dp, bottom = 20.dp))
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {

                                Text(text = it.costeLinea.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                                )
                            }
                            //Spacer(modifier = Modifier.padding(start = 40.dp, bottom = 20.dp))
                        }
                    }

                }

            item {
                Button(
                    onClick = {
                        //Enviar a la BD
                        mainViewModelCreateOrder.pedido = Pedidos(
                            _id = mainViewModelCreateOrder.orderByTable._id,
                            lineasPedido = mainViewModelCreateOrder.lineasPedidos,
                            idMesa = tableId
                        )

                        mainViewModelCreateOrder.editOrder(mainViewModelCreateOrder.pedido)
                        navController.navigate(Destinations.AccessToTables.route) {
                            popUpTo(0)
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            PaddingValues(
                                start = 20.dp,
                                end = 20.dp
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 6.dp,
                        end = 10.dp,
                        bottom = 6.dp
                    ),

                    ) {
                    Text(text = "Enviar pedido a cocina")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview21() {

}
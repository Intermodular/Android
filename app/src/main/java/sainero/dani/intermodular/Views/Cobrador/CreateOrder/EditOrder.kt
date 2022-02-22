package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import sainero.dani.intermodular.DataClass.*
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.currentValidateUser
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelPedidos
import sainero.dani.intermodular.ViewsItems.confirmAlertDialog
import java.time.LocalDateTime
import kotlin.math.roundToLong

class EditOrder : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainEditOrder(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    tableId: Int,
    viemModelPedidos: ViewModelPedidos,
    viewModelMesas: ViewModelMesas,
    typeId: Int
) {
    //Variables
    var (refresh, onValueChangeRefresh) = rememberSaveable { mutableStateOf(true) }

    Scaffold(

        content = {
            if (refresh)
                CreateContent(
                    mainViewModelCreateOrder = mainViewModelCreateOrder,
                    onValueChangeRefresh = onValueChangeRefresh,
                    tableId = tableId,
                    viemModelPedidos = viemModelPedidos,
                    viewModelMesas = viewModelMesas,
                    typeId = typeId
                )

        }
    )


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CreateContent(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    tableId: Int,
    viemModelPedidos: ViewModelPedidos,
    viewModelMesas: ViewModelMesas,
    onValueChangeRefresh: (Boolean)-> Unit,
    typeId: Int
){
    var selectedTable = remember{ Mesas(_id = 0,state = "Libre",number = 0,numChair = 0,zone = "")}
    viewModelMesas.mesasListResponse.forEach { if (it._id.equals(tableId)) selectedTable = it}
    var indexItem : MutableState<Int> = remember{ mutableStateOf(0)}
    var (deleteLane,onValueChangeDeleteLane) = remember { mutableStateOf(false) }
    var lineaPedidoSeleccionada = remember { mutableStateOf(LineaPedido(Productos(0,"","", arrayListOf(),0f, arrayListOf(),"",0),0,"", arrayListOf(),0f))}
    val expanded = remember { mutableStateOf(false)}
    val current = LocalContext.current
    var (changeOrderToAnotherTable,onValueChangeChangeOrderToAnotherTable) = remember { mutableStateOf(false) }

    if (deleteLane) {
        var title: String = "¿Seguro que desea eliminar la linea de pedido seleccionada?"
        var subtitle: String = "Nombre " + lineaPedidoSeleccionada.value.producto.name + ". Cantidad: " + lineaPedidoSeleccionada.value.cantidad.toString()

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteLane,
        ) {
            if (it) {
                mainViewModelCreateOrder.lineasPedidos.removeAt(indexItem.value)
                lineaPedidoSeleccionada.value = LineaPedido(Productos(0,"","", arrayListOf(),0f, arrayListOf(),"",0),0,"", arrayListOf(),0f)
                onValueChangeDeleteLane(false)
            }
        }
    }
    if (changeOrderToAnotherTable) {
        changeOrderToAnotherTable(
            onValueChangeChangeOrderToAnotherTable = onValueChangeChangeOrderToAnotherTable,
            mainViewModelCreateOrder = mainViewModelCreateOrder
        )
    }
    Scaffold(
         topBar = {
              TopAppBar(
                  title = {
                      Text(text = "Pedido mesa ${selectedTable.number}", fontSize = 20.sp)
                  },
                  navigationIcon = {
                      IconButton(
                          onClick = {
                              navController.popBackStack()
                          }
                      ) {
                          Icon(
                              Icons.Filled.ArrowBack,
                              contentDescription = "",
                              tint = Color.White
                          )
                      }
                  },
                  backgroundColor = MaterialTheme.colors.primary,
                  elevation = AppBarDefaults.TopAppBarElevation,
                  actions = {

                      Box (Modifier.wrapContentSize()){
                          IconButton(onClick = {
                              expanded.value = true
                          }) {
                              Icon(
                                  Icons.Filled.MoreVert,
                                  contentDescription = "More icon"
                              )
                          }

                          DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                              DropdownMenuItem(
                                  onClick = {
                                      expanded.value = false
                                      onValueChangeChangeOrderToAnotherTable(true)
                                  }) {
                                  Text(text = "Mover Pedido de mesa")
                              }
                              DropdownMenuItem(
                                  onClick = {
                                      val pedido: Pedidos = Pedidos(
                                          _id = mainViewModelCreateOrder.pedido._id,
                                          lineasPedido = mainViewModelCreateOrder.lineasPedidos.toMutableList(),
                                          idMesa = mainViewModelCreateOrder.pedido.idMesa
                                      )

                                      val ticket: Ticket = Ticket(
                                          _id = 0,
                                          empleado = currentValidateUser,
                                          costeTotal = mainViewModelCreateOrder.calculateTotalPrice(),
                                          lineasPedido = pedido.lineasPedido,
                                          metodoPago = "Tarjeta",
                                          numMesa = selectedTable.number,
                                          fechaYHoraDePago = LocalDateTime.now().toString()
                                      )

                                      mainViewModelCreateOrder.uploadTicket(ticket = ticket)
                                      mainViewModelCreateOrder.deleteOrder(mainViewModelCreateOrder.pedido._id,mainViewModelCreateOrder.pedido.idMesa)
                                      navController.navigate(Destinations.AccessToTables.route) {
                                          popUpTo(Destinations.Login.route)
                                      }
                                      Toast.makeText(current,"El pedido ha sido pagado correctamente",Toast.LENGTH_SHORT).show()

                                      expanded.value = false
                                  }) {
                                  Text(text = "Marcar pedido como cobrado con tarjeta")
                              }
                              DropdownMenuItem(
                                  onClick = {
                                      mainViewModelCreateOrder.lineasPedidos.clear()
                                      mainViewModelCreateOrder.lineasExtras.clear()
                                      Toast.makeText(current,"El pedido se ha vaciado correctamente",Toast.LENGTH_SHORT).show()
                                      navController.popBackStack()
                                      expanded.value = false
                                  }) {
                                  Text(text = "Vaciar pedido")
                              }
                              DropdownMenuItem(
                                  onClick = {
                                      mainViewModelCreateOrder.deleteOrder(id = mainViewModelCreateOrder.pedido._id,mainViewModelCreateOrder.pedido.idMesa)
                                          Toast.makeText(current,"El pedido ha sido eliminado correctamente",Toast.LENGTH_SHORT).show()
                                          navController.navigate(Destinations.AccessToTables.route) {
                                              popUpTo(Destinations.Login.route)
                                          }
                                      expanded.value = false
                                  }) {
                                  Text(text = "Borrar pedido")
                              }
                          }
                      }

                  },
              )
         },
         content = {
             Column(
                 verticalArrangement = Arrangement.SpaceEvenly
             ) {
                 LazyColumn(
                     Modifier.fillMaxHeight(0.9f),
                     content = {
                         item {
                             Spacer(modifier = Modifier.padding(10.dp))
                             Row(
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
                                                     lineaPedidoSeleccionada.value =
                                                         mainViewModelCreateOrder.lineasPedidos.get(
                                                             index
                                                         )
                                                     indexItem.value = index
                                                     onValueChangeDeleteLane(true)
                                                 },
                                                 onTap = { Offset ->
                                                     //val idLineaPedido = mainViewModelCreateOrder.lineasPedidos.indexOf(it)
                                                     mainViewModelCreateOrder.editLineOrder = it
                                                     mainViewModelCreateOrder.editLineOrderIndex =
                                                         index
                                                     navController.navigate("${Destinations.EditOrderLine.route}/${typeId}")
                                                 }
                                             )
                                         }
                                 ) {
                                     Column(
                                         horizontalAlignment = Alignment.Start
                                     ) {
                                         var string = "${it.producto.name}\n"

                                         it.lineasExtras.forEach{if (it.cantidad > 0) string += "   EXTRA: ${it.extra.name}\n" }
                                         Text(
                                             text = string,
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

                                         Text(
                                             text = it.costeLinea.roundToLong().toString(),
                                             textAlign = TextAlign.Center,
                                             modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                                         )
                                     }
                                 }
                             }

                         }
                     }
                 )
                 Button(
                     onClick = {
                         //Enviar a la BD
                         mainViewModelCreateOrder.pedido = Pedidos(
                             _id = mainViewModelCreateOrder.orderByTable._id,
                             lineasPedido = mainViewModelCreateOrder.lineasPedidos,
                             idMesa = tableId
                         )

                         mainViewModelCreateOrder.editOrder(mainViewModelCreateOrder.pedido) {
                             navController.navigate(Destinations.AccessToTables.route) {
                                 popUpTo(Destinations.Login.route)
                             }
                             Toast.makeText(
                                 current, "El pedido ha sido transladado correctamente",
                                 Toast.LENGTH_SHORT
                             ).show()
                         }
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
                     contentPadding = PaddingValues(
                         start = 10.dp,
                         top = 6.dp,
                         end = 10.dp,
                         bottom = 6.dp
                     ),
                 )
                 {
                     Text(text = "Enviar pedido a cocina")
                 }
             }

         }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun changeOrderToAnotherTable(
    onValueChangeChangeOrderToAnotherTable: (Boolean) -> Unit,
    mainViewModelCreateOrder: MainViewModelCreateOrder
) {
    val createTables = remember{ mutableStateOf(false)}
    MaterialTheme {
        Dialog(
            onDismissRequest = {
                onValueChangeChangeOrderToAnotherTable(false)
            },
            properties = DialogProperties(

            ),
            content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ){
                    mainViewModelCreateOrder.getMesaList {
                        createTables.value = true
                    }

                    if (createTables.value) {
                        createTables(
                            mainViewModelCreateOrder = mainViewModelCreateOrder,
                            onValueChangeDisableAlert = onValueChangeChangeOrderToAnotherTable,
                        )
                    }
                }
            }
        )
    }
}




@ExperimentalFoundationApi
@Composable
private fun createTables(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    onValueChangeDisableAlert:  (Boolean) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var current = LocalContext.current

    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Eligue la mesa a la que desea enviar el pedido", fontSize = 20.sp)
        LazyVerticalGrid(
            cells = GridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
        ) {
            mainViewModelCreateOrder.mesasListResponse.forEach { item ->
                item {
                    Box(
                        Modifier
                            .padding(10.dp)
                    ) {
                        Button(
                            onClick = {
                                onValueChangeDisableAlert(false)
                                    mainViewModelCreateOrder.getOrderByTableWithDelay(id = item._id) {
                                    if (it) {
                                        Toast.makeText(current,"La mesa seleccionada ya tiene un pedido",Toast.LENGTH_SHORT).show()
                                    }
                                    else {
                                         mainViewModelCreateOrder.deleteOrder(
                                             id = mainViewModelCreateOrder.pedido._id,
                                             idMesa = mainViewModelCreateOrder.pedido.idMesa
                                         )

                                        mainViewModelCreateOrder.pedido = Pedidos(
                                            _id = mainViewModelCreateOrder.pedido._id,
                                            idMesa = item._id,
                                            lineasPedido = mainViewModelCreateOrder.lineasPedidos
                                        )

                                        mainViewModelCreateOrder.uploadOrder(mainViewModelCreateOrder.pedido){}
                                        navController.navigate(Destinations.AccessToTables.route) {
                                            popUpTo(Destinations.Login.route)
                                        }
                                        Toast.makeText(current,"El pedido ha sido transladado correctamente",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            modifier = Modifier,
                            contentPadding = PaddingValues(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (item.state.equals("Libre")) Color.Green else Color.Red,
                                contentColor = Color.Blue
                            )
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = "${item.zone} (${item.numChair})",
                                    fontSize = 10.sp,
                                    modifier = Modifier.fillMaxSize(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = item.number.toString(),
                                    fontSize = 20.sp,
                                    modifier = Modifier.fillMaxSize(),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.padding(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview21() {

}
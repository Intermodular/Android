package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.*
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.currentValidateUser
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelPedidos
import sainero.dani.intermodular.ViewsItems.confirmAlertDialog
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import java.time.LocalDateTime
import kotlin.math.roundToLong

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
    val context = LocalContext.current
    var (changeOrderToAnotherTable,onValueChangeChangeOrderToAnotherTable) = remember { mutableStateOf(false) }
    var (payOrder,onValueChangePayOrder) = remember { mutableStateOf(false) }

    if (payOrder) {
        payOrder(
            mainViewModelCreateOrder = mainViewModelCreateOrder,
            onValueChangePayOrder = onValueChangePayOrder,
            selectedTable = selectedTable
        )
    }

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
                                  contentDescription = "More icon",
                                  tint = Color.White
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
                                      if (mainViewModelCreateOrder.lineasPedidos.size == 0)
                                          Toast.makeText(context, "El pedido esta vacio", Toast.LENGTH_SHORT).show()
                                      else
                                          onValueChangePayOrder(true)
                                      expanded.value = false
                                  }
                              ) {
                                  Text(text = "Cobrar el pedido")
                              }
                              DropdownMenuItem(
                                  onClick = {
                                      if (mainViewModelCreateOrder.lineasPedidos.size == 0) {
                                          Toast.makeText(context, "El pedido ya esta vacio", Toast.LENGTH_SHORT).show()
                                      }
                                      else {
                                          mainViewModelCreateOrder.lineasPedidos.clear()
                                          mainViewModelCreateOrder.lineasExtras.clear()
                                          Toast.makeText(context,"El pedido se ha vaciado correctamente",Toast.LENGTH_SHORT).show()
                                          navController.popBackStack()
                                      }
                                      expanded.value = false
                                  }
                              ) {
                                  Text(text = "Vaciar pedido")
                              }
                              DropdownMenuItem(
                                  onClick = {
                                      mainViewModelCreateOrder.deleteOrder(id = mainViewModelCreateOrder.pedido._id,mainViewModelCreateOrder.pedido.idMesa)
                                          Toast.makeText(context,"El pedido ha sido eliminado correctamente",Toast.LENGTH_SHORT).show()
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
                             Column{
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
                                     Column(
                                         horizontalAlignment = Alignment.End
                                     ) {

                                         Text(
                                             text =  "${"%.2f".format(it.costeLinea).toFloat().toString()}",
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
                             Toast.makeText(context, "El pedido ha sido transladado correctamente", Toast.LENGTH_SHORT).show()
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
                            .fillMaxWidth(1.1f)
                            .fillMaxHeight(0.9f)
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
        verticalArrangement = Arrangement.SpaceEvenly,

    ) {
        Text(text = "Eligue la mesa a la que desea enviar el pedido", fontSize = 20.sp, textAlign = TextAlign.Center)
        LazyVerticalGrid(
            cells = GridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
        ) {
            mainViewModelCreateOrder.mesasListResponse.forEach { item ->
                item {
                    Card(
                        Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(

                                    onTap = { Offset ->
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
                                    }
                                )
                            }
                            .padding(PaddingValues(10.dp)),
                        backgroundColor = checkState(item.state),
                        elevation = 6.dp,
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = "${item.zone} (${item.numChair})",
                                fontSize = 10.sp,
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.padding(2.dp))

                            Text(
                                text = item.number.toString(),
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun payOrder(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    onValueChangePayOrder: (Boolean) -> Unit,
    selectedTable: Mesas
) {
    var current = LocalContext.current

    Dialog(
        onDismissRequest = {
            onValueChangePayOrder(false)
        },
        properties = DialogProperties(

        ),
        content = {

            //Declaración de varaibles
            val isChecked = remember { mutableStateOf(false) }

            val pedido: Pedidos = Pedidos(
                _id = mainViewModelCreateOrder.pedido._id,
                lineasPedido = mainViewModelCreateOrder.lineasPedidos.toMutableList(),
                idMesa = mainViewModelCreateOrder.pedido.idMesa
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .background(Color.White)
            ){
                Spacer(modifier = Modifier.padding(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(
                        text = "Seleccione como desea realizar el pago",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Tarjeta", color = if (!isChecked.value) Color.Blue else Color.Black)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Switch(
                        checked = isChecked.value,
                        onCheckedChange = { isChecked.value = it }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Efectivo", color = if (isChecked.value) Color.Blue else Color.Black)
                }
                Spacer(modifier = Modifier.padding(10.dp))

                if (isChecked.value) {
                    payWithCash(
                        mainViewModelCreateOrder = mainViewModelCreateOrder,
                        selectedTable = selectedTable,
                        current = current,
                        pedido = pedido
                    )
                }
                else {
                    payWithCard(
                        mainViewModelCreateOrder = mainViewModelCreateOrder,
                        selectedTable = selectedTable,
                        current = current,
                        pedido = pedido
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun payWithCash(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    selectedTable: Mesas,
    pedido: Pedidos,
    current: Context
){
    var (textMoneyOfUser, onValueChangetextMoneyOfUser) = rememberSaveable{mutableStateOf("0")}
    var (moneyOfUserError,moneyOfUserErrorChange) = remember { mutableStateOf(false) }
    val moneyOfUserOfNameError: String = "El número debe de estar sin ','"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedTextField(
                value = textMoneyOfUser,
                onValueChange = {
                    onValueChangetextMoneyOfUser(it)
                    moneyOfUserErrorChange(!mainViewModelCreateOrder.isValidCostOfProduct(it))
                },
                placeholder = { Text("Efectivo recibido") },
                label = { Text(text = "Efectivo recibido") },
                isError = moneyOfUserError,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            )
            val assistiveElementText = if (moneyOfUserError) moneyOfUserOfNameError  else ""
            val assistiveElementColor = if (moneyOfUserError) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }
            Text(
                text = assistiveElementText,
                color = assistiveElementColor,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.padding(10.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Total del pedido = ${mainViewModelCreateOrder.calculateTotalPrice()}€")
    }

    Spacer(modifier = Modifier.padding(3.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Total a devolver = ${mainViewModelCreateOrder.calculateTotalGiveBack(if(textMoneyOfUser.equals("")) 0f else textMoneyOfUser.toFloat())}€")
    }

    Spacer(modifier = Modifier.padding(5.dp))
    Button(
        onClick = {
            val ticket: Ticket = Ticket(
                _id = 0,
                empleado = currentValidateUser,
                costeTotal = mainViewModelCreateOrder.calculateTotalPrice(),
                lineasPedido = pedido.lineasPedido,
                metodoPago = "Efectivo",
                numMesa = selectedTable.number,
                fechaYHoraDePago = LocalDateTime.now().toString()
            )

            uplaodTicket(
                ticket = ticket,
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                current = current
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Text(text = "Pagar pedido en Efectivo", textAlign = TextAlign.Center)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun payWithCard(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    selectedTable: Mesas,
    pedido: Pedidos,
    current: Context
) {
    Spacer(modifier = Modifier.padding(5.dp))

    Button(
        onClick = {
            val ticket: Ticket = Ticket(
                _id = 0,
                empleado = currentValidateUser,
                costeTotal = mainViewModelCreateOrder.calculateTotalPrice(),
                lineasPedido = pedido.lineasPedido,
                metodoPago = "Tarjeta",
                numMesa = selectedTable.number,
                fechaYHoraDePago = LocalDateTime.now().toString()
            )

            uplaodTicket(
                ticket = ticket,
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                current = current
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Text(text = "Pagar con tarjeta", textAlign = TextAlign.Center)
    }
}

private fun uplaodTicket(
    ticket: Ticket,
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    current: Context,
) {
    mainViewModelCreateOrder.uploadTicket(ticket = ticket)
    mainViewModelCreateOrder.deleteOrder(mainViewModelCreateOrder.pedido._id,mainViewModelCreateOrder.pedido.idMesa)
    navController.navigate(Destinations.AccessToTables.route) {
        popUpTo(Destinations.Login.route)
    }
    Toast.makeText(current,"El pedido ha sido pagado correctamente",Toast.LENGTH_SHORT).show()
}

private fun checkState(state: String): Color {
    when(state){
        "Libre" -> return Color(0xFF2EEE5D)
        "Ocupada" -> return Color(0xFFEE2E31)
        "Reservada" -> return Color.Yellow
        else -> {
            Color.White
        }
    }
    return Color.White
}
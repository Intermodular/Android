package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Pedidos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelPedidos
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.ui.theme.IntermodularTheme

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

    var selectedTable = remember{ Mesas(_id = 0,state = "Libre",number = 0,numChair = 0,zone = "")}
    viewModelMesas.mesasListResponse.forEach { if (it._id.equals(tableId)) selectedTable = it}

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
            mainViewModelCreateOrder._lineasPedidos.forEach{
                item {
                    Column(
                        //verticalArrangement = Arrangement.SpaceAround,
                        //horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 25.dp, bottom = 20.dp, end = 25.dp)
                                .clickable {
                                    mainViewModelCreateOrder.pedidoEditar = it

                                    //navController.navigate(Destinations.CreateOrder.route)
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
                                //val priceExtras = it.lineasExtra.forEach()
                                //val result = (it.cantidad * it.producto.price) + it.lineasExtra.get(0).extra.price * it.lineasExtra.get(0).cantidad)
                               /* Text(text = ((it.cantidad * it.producto.price) + it.lineasExtra.get(0).extra.price).toString(),
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 3)
                                )*/
                            }
                            //Spacer(modifier = Modifier.padding(start = 40.dp, bottom = 20.dp))
                        }
                    }

                }
            }
            item {
                Button(
                    onClick = {
                        //Enviar a la BD
                        viemModelPedidos.getOrderList()

                        mainViewModelCreateOrder.pedido =
                            Pedidos(
                            _id = mainViewModelCreateOrder.pedido!!._id,
                            idMesa = tableId,
                            lineasPedido = mainViewModelCreateOrder._lineasPedidos
                        )


                        if (mainViewModelCreateOrder.editOrder) {
                            viemModelPedidos.editOrder(mainViewModelCreateOrder.pedido!!)
                        }
                        else {
                            viemModelPedidos.uploadOrder(mainViewModelCreateOrder.pedido!!)
                            mainViewModelCreateOrder.editOrder = true
                        }
                        selectedTable.state = "Ocupada"
                        viewModelMesas.editMesa(mesa = selectedTable)
                        navController.navigate(Destinations.AccessToTables.route) {
                            popUpTo(Destinations.Login.route)
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
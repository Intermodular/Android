package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sainero.dani.intermodular.DataClass.Pedidos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.ui.theme.IntermodularTheme

class EditOrder : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainEditOrder(mainViewModelCreateOrder: MainViewModelCreateOrder, tableId: Int) {
    //mainViewModelCreateOrder.pedido = Pedidos(0,tableId,mainViewModelCreateOrder._lineasPedidos)
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

                                    GlobalVariables.navController.navigate(Destinations.CreateOrder.route)
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
                        //enviara

                    }
                ) {
                    Text(text = "Enviar")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview21() {

}
package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import sainero.dani.intermodular.DataClass.LineaExtra
import sainero.dani.intermodular.DataClass.LineaPedido
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelTipos

class CreateOrderLine : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainCreateOrderLine(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    viewModelProductos: ViewModelProductos,
    viewModelTipos: ViewModelTipos,
    productId: Int,
    typeId: Int
) {
    //Variables de ayuda
    val expanded = remember { mutableStateOf(false)}


    //Textos
    var description = rememberSaveable { mutableStateOf("") }
    var textQuantity = rememberSaveable{ mutableStateOf("1") }

    var product: Productos = Productos(0,"","", arrayListOf(),0f, arrayListOf(),"",0)
    viewModelProductos.productListResponse.forEach { if (it._id.equals(productId)) product = it }
    var selectedType: Tipos = Tipos(0,"","", arrayListOf())
    viewModelTipos.typeListResponse.forEach { if (it._id.equals(typeId)) selectedType = it }
    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Editar producto",color = Color.White)

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
                                     navController.navigate("${Destinations.ProductInformation.route}/${product._id}")
                                 }) {
                                 Text(text = "Ver información del producto")
                             }
                         }
                     }
                 }
             )

        } ,
        content = {
            LazyColumn(
                content = {
                    item {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {


                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier.height(200.dp)
                                ) {
                                    Image(
                                        painter =  rememberImagePainter(
                                            data =  if (!product.img.equals("")) "${product.img}" else "https://www.chollosocial.com/media/data/2019/11/678gf34.png",
                                            builder = {
                                                //transformations(CircleCropTransformation())

                                            }
                                        ),
                                        contentDescription ="Imágen del producto",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                Box(
                                    modifier = Modifier.height(50.dp)
                                ) {
                                    Text(
                                        text = product.name,
                                        fontSize = 20.sp,
                                        modifier = Modifier.fillMaxSize(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.padding(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {

                                    OutlinedTextField(
                                        value = textQuantity.value,
                                        onValueChange = {
                                            textQuantity.value = it
                                        },
                                        placeholder = { Text("1") },
                                        label = {Text(text = "Cantidad:")},
                                        modifier = Modifier
                                            .padding(start = 20.dp, end = 20.dp)
                                            .fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType =  KeyboardType.Number)
                                    )
                                }
                                Spacer(modifier = Modifier.padding(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {

                                    TextField(
                                        value = description.value,
                                        onValueChange = { description.value = it },
                                        label = { Text("Anotaciones") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .padding(PaddingValues(start = 20.dp, end = 20.dp)),

                                        )
                                }
                            }

                            selectedType.compatibleExtras.forEach {
                                var numExtra = rememberSaveable { mutableStateOf("0") }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp, bottom = 20.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            //Restar 1
                                            if (numExtra.value.toInt() > 0){
                                                numExtra.value = (numExtra.value.toInt() - 1).toString()
                                            }


                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.remove_circle_outline),
                                            contentDescription = "Less icon",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(start = 20.dp)
                                        )
                                    }

                                    TextField(
                                        value = numExtra.value,
                                        onValueChange = { numExtra.value = it },
                                        label = { Text(it.name, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                                        modifier = Modifier
                                            .width((LocalConfiguration.current.screenWidthDp / 2).dp)
                                            .height(60.dp)
                                            .padding(PaddingValues(start = 20.dp, end = 20.dp)),
                                        enabled = false,
                                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                                    )

                                    IconButton(
                                        onClick = {
                                            //Sumar 1
                                            if (numExtra.value.toInt() + 1 < 10){
                                                numExtra.value = (numExtra.value.toInt() + 1).toString()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.add_circle_outline),
                                            contentDescription = "More icon",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(end = 20.dp)
                                        )
                                    }
                                }
                                val lineaExtra : LineaExtra = LineaExtra(it,numExtra.value.toInt())
                                mainViewModelCreateOrder.addLineaExtras(lineaExtra)
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(end = 40.dp, start = 40.dp, bottom = 30.dp),

                                ) {
                                Row(
                                    Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Button(
                                        onClick = {
                                            navController.popBackStack()
                                        },
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
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(50.dp)
                                    ) {
                                        Text(text = "Cancelar")
                                    }
                                }

                                Row(
                                    Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = {
                                            var lineaDePedido = LineaPedido(producto = product,
                                                anotaciones = description.value,
                                                cantidad = textQuantity.value.toInt(),
                                                costeLinea = 0f,
                                                lineasExtra = mainViewModelCreateOrder._lineasExtras
                                            )
                                            mainViewModelCreateOrder._lineasPedidos.remove(mainViewModelCreateOrder.pedidoEditar)
                                            mainViewModelCreateOrder.addLineaPedido(lineaDePedido)
                                            navController.popBackStack()
                                        },
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
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(50.dp)
                                    ) {
                                        Text(text = "Guardar")

                                    }
                                }

                            }

                        }
                    }
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview22() {

}
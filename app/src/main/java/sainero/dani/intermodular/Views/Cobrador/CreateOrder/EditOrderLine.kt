package sainero.dani.intermodular.Views.Cobrador.CreateOrder

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import sainero.dani.intermodular.DataClass.*
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelPedidos
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.ViewsItems.confirmAlertDialog
import sainero.dani.intermodular.ViewsItems.selectedDropDownMenu
import java.lang.NumberFormatException

@Composable
fun MainEditOrderLine(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    viewModelTipos: ViewModelTipos,
    typeId: Int
) {
//Variables de ayuda
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val getCompatibleExtras = remember { mutableStateOf(true)}
    val firsthAcces = remember { mutableStateOf(true) }
    //Textos
    var description = rememberSaveable { mutableStateOf(mainViewModelCreateOrder.editLineOrder.anotaciones) }

    var textQuantity = rememberSaveable{ mutableStateOf(mainViewModelCreateOrder.editLineOrder.cantidad.toString()) }
    var quantityError = remember { mutableStateOf(false) }
    val textOfQuantityError: String = "Debe ser un número entero"

    var product: Productos = mainViewModelCreateOrder.editLineOrder.producto
    var selectedType = remember { mutableStateOf(Tipos(0,"","", arrayListOf()))}
    viewModelTipos.typeListResponse.forEach { if (it.name.equals(mainViewModelCreateOrder.editLineOrder.producto.type)) selectedType.value = it }



    if (firsthAcces.value){
        mainViewModelCreateOrder.lineasExtras.clear()
        firsthAcces.value = false
    }

    if (getCompatibleExtras.value) {
        mainViewModelCreateOrder.editLineOrder.lineasExtras.forEach{lineaExtra -> mainViewModelCreateOrder.lineasExtras.add(lineaExtra) }
        mainViewModelCreateOrder.saveEditLine()
        getCompatibleExtras.value = false
    }



    val (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}
    if (deleteItem){
        var title: String = "¿Seguro que desea eliminar la línea seleccionada?"
        var subtitle: String = "No podrás volver a recuperarla"

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
        ) {
            if (it) {
                //Eliminar línea
                mainViewModelCreateOrder.lineasPedidos.removeAt(mainViewModelCreateOrder.editLineOrderIndex)
                Toast.makeText(context,"La línea ha sido eliminada correctamente",Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }

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
                    IconButton(
                        onClick = {
                            onValueChangeDeleteItem(true)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar empleado",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
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
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                LazyColumn(
                    content = {
                        item {
                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier.height(200.dp)
                                ) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = if (!product.img.equals("")) "${product.img}" else "https://www.chollosocial.com/media/data/2019/11/678gf34.png",
                                            builder = {

                                            }
                                        ),
                                        contentDescription = "Imágen del producto",
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
                                            if (mainViewModelCreateOrder.isInteger(it) || it.equals("")) {
                                                textQuantity.value = it
                                                quantityError.value = false
                                            } else {
                                                quantityError.value = true
                                            }
                                        },
                                        placeholder = { Text("Cantidad") },
                                        label = { Text(text = "Cantidad:") },
                                        isError = quantityError.value,
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                        modifier = Modifier
                                            .padding(start = 20.dp, end = 20.dp)
                                            .fillMaxWidth(),
                                    )
                                }
                                Spacer(modifier = Modifier.padding(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    TextField(
                                        value = getCorrectFormatInAnotation(description.value),
                                        onValueChange = { description.value = it },
                                        label = { Text("Anotaciones") },
                                         modifier = Modifier
                                             .fillMaxWidth()
                                             .height(100.dp)
                                             .padding(PaddingValues(start = 20.dp, end = 20.dp)),
                                    )
                                }
                            }
                        }
                        itemsIndexed(mainViewModelCreateOrder.editLineOrder.lineasExtras) { index, it ->
                            var numExtra = rememberSaveable { mutableStateOf(it.cantidad.toString()) }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, bottom = 20.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        if (numExtra.value.toInt() > 0) {
                                            numExtra.value = (numExtra.value.toInt() - 1).toString()
                                            mainViewModelCreateOrder.lineasExtras.get(index).cantidad = numExtra.value.toInt()
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
                                    label = {
                                        Text(
                                            "${it.extra.name} (${it.extra.price})",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    },
                                    modifier = Modifier
                                        .width((LocalConfiguration.current.screenWidthDp / 2).dp)
                                        .height(60.dp)
                                        .padding(PaddingValues(start = 20.dp, end = 20.dp)),
                                    enabled = false,
                                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                                )
                                IconButton(
                                    onClick = {
                                        if (numExtra.value.toInt() + 1 < 10) {
                                            numExtra.value = (numExtra.value.toInt() + 1).toString()
                                            mainViewModelCreateOrder.lineasExtras.get(index).cantidad = numExtra.value.toInt()
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

                        }
                         item {
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
                                            mainViewModelCreateOrder.restoreEditLine()
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

                                            var linePrice = mainViewModelCreateOrder.calculateLinePrice(
                                                mainViewModelCreateOrder = mainViewModelCreateOrder,
                                                product = product,
                                                textQuantity = if (!textQuantity.value.equals("")) textQuantity.value.toInt() else 1
                                            )

                                            var lineaDePedido = LineaPedido(
                                                producto = product,
                                                anotaciones = description.value,
                                                cantidad = if (!textQuantity.value.equals("")) textQuantity.value.toInt() else 1,
                                                costeLinea = linePrice,
                                                lineasExtras = mainViewModelCreateOrder.lineasExtras.toMutableList()
                                            )

                                            mainViewModelCreateOrder.lineasPedidos[mainViewModelCreateOrder.editLineOrderIndex] = lineaDePedido

                                            Toast.makeText(context,"Ha sido actualizado correctamente",Toast.LENGTH_SHORT).show()
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
                )
            }
        }
    )
}

fun getCorrectFormatInAnotation(text: String): String {
    var result = text.replace(",","\n")
    return result
}
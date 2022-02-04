package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelProductos

@ExperimentalFoundationApi
class NewProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainNewProduct(viewModelProductos: ViewModelProductos) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }

    //Textos

    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf("") }
    var (textTipe, onValueChangeTipe) = rememberSaveable{ mutableStateOf("") }
    var ingredientes: MutableList<String> = mutableListOf()
    var (textCost, onValueChangeCost) = rememberSaveable{ mutableStateOf("0") }
    var especification: MutableList<String> = mutableListOf()
    var (textImg, onValueChangeImg) = rememberSaveable{ mutableStateOf("") }
    var (textStock, onValueChangeStock) = rememberSaveable{ mutableStateOf("0") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nuevo producto",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {


                }
            )

        },
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {

                createRowList("Nombre",textName,onValueChangeName)
                createRowList("Tipo",textTipe,onValueChangeTipe)
                dropDownMenu("Ingredientes",ingredientes)
                createRowList("Coste",textCost,onValueChangeCost)
                dropDownMenu("Especificaciones",especification)
                createRowList("Imágen",textImg,onValueChangeImg)
                createRowList("Stock",textStock,onValueChangeStock)

                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            textName = ""
                            textTipe = ""
                            textCost = ""
                            textImg = ""
                            textStock = ""
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
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Resetear campos",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Resetear campos", fontSize = 15.sp)
                    }

                    Button(
                        onClick = {
                            //Cambiar por validación de solo numeros en coste
                            val product: Productos
                            if (!textCost.equals(""))
                                 product = Productos(
                                    _id = 0,
                                    name = textName,
                                    type = textTipe,
                                    ingredients = ingredientes,
                                    price = textCost.toFloat(),
                                    especifications = especification,
                                    img = textImg,
                                    stock = textStock.toInt()
                                )
                            else
                             product = Productos(
                                _id = 0,
                                name = textName,
                                type = textTipe,
                                ingredients = ingredientes,
                                price = 0f,
                                especifications = especification,
                                img = textImg,
                                stock = textStock.toInt()
                            )
                            viewModelProductos.uploadProduct(product = product)
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
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Crear producto",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Crear producto", fontSize = 15.sp)
                    }

                }
            }

        }
    )
}

@Composable
private fun createRowList(text: String, value: String, onValueChange: (String) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text) },
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
        )
    }
}


@Composable
private fun dropDownMenu(text: String,suggestions: List<String>) {
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    var editItem = remember{ mutableStateOf(false)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        Column() {

            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(icon, "arrowExpanded",
                        Modifier.clickable { expanded = !expanded })
                },
                leadingIcon = {
                    Icon(Icons.Default.Edit,"Edit ${text}",
                        Modifier.clickable{
                            editItem.value = true
                        })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                        expanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview12() {
        //MainNewProduct()
}
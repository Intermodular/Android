package sainero.dani.intermodular.Views.Cobrador

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.Views.Administration.Products.Especifications.Especifications

class CreateOrder : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainCreateOrder(tableId: Int, viewModelProductos: ViewModelProductos, viewModelTipos: ViewModelTipos, viewModelMesas: ViewModelMesas) {

    //Busquedas BD
    val allTypes = viewModelTipos.typeListResponse
    var allProducts = viewModelProductos.productListResponse
    var allTypesNames: MutableList<String> = mutableListOf()
    allTypes.forEach { allTypesNames.add(it.name) }
    var allTables = viewModelMesas.mesasListResponse
    var selectedTable: Mesas = Mesas(0,"",0,"",0)
    allTables.forEach { if (it._id.equals(tableId)) selectedTable = it }

    //Filtros
    val (isCheckedVegano,onValueChangeVegano) = remember { mutableStateOf(false) }
    val (isCheckedVegetariano,onValueChangeVegetariano) = remember { mutableStateOf(false) }
    val (isCheckedPescetariano,onValueChangePescetariano) = remember { mutableStateOf(false) }
    val (isCheckedSinGluten,onValueChangeSinGluten) = remember { mutableStateOf(false) }
    val (isCheckedPicante,onValueChangePicante) = remember { mutableStateOf(false) }
    var lisOfFilterEspecifications: MutableList<String> = remember { mutableListOf()}

    //Variables de extra
    val (informationProduct,onValueChangedInformationProduct) = remember { mutableStateOf(false) }
    val (selectedProduct,onValueChangeSelectedProduct) = remember { mutableStateOf(Productos(0,"","", arrayListOf(),0f, arrayListOf(),"",0))}


    //Variables de ayuda
    var state = remember { mutableStateOf(0)}
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var nameOfSelectedType = remember { mutableStateOf(allTypesNames[0])}

    //No obtiene el tipo seleccionado
    var selectedType = remember { Tipos(0,"","", arrayListOf(),)}
    allTypes.forEach { if (it.name.equals(nameOfSelectedType)) selectedType = it }

    val scope = rememberCoroutineScope()



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Mesa número ${selectedTable.number}")
                },
                actions = {

                },
                navigationIcon = {

                    IconButton(
                        onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "")
                    }
                }
            )
        },
        drawerContent = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .background(MaterialTheme.colors.primary),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Filtros",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    LabelledCheckbox(
                        labelText = "Vegano",
                        isCheckedValue = isCheckedVegano,
                        onValueChangeCheked = onValueChangeVegano
                    )
                    LabelledCheckbox(
                        labelText = "Vegetariano",
                        isCheckedValue = isCheckedVegetariano,
                        onValueChangeCheked = onValueChangeVegetariano
                    )
                    LabelledCheckbox(
                        labelText = "Pescetariano",
                        isCheckedValue = isCheckedPescetariano,
                        onValueChangeCheked = onValueChangePescetariano
                    )
                    LabelledCheckbox(
                        labelText = "Sin Gluten",
                        isCheckedValue = isCheckedSinGluten,
                        onValueChangeCheked = onValueChangeSinGluten
                    )
                    LabelledCheckbox(
                        labelText = "Picante",
                        isCheckedValue = isCheckedPicante,
                        onValueChangeCheked = onValueChangePicante
                    )
                }

        },
        content = {
            //Crear filtro
            if (informationProduct) {
                createAllProductEspecifications(producto = selectedProduct,selectedType = selectedType)
            } else {
                Column(
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    ScrollableTabRow(
                        selectedTabIndex = state.value,
                        divider = {
                            /* Divider(
                                 modifier = Modifier
                                     .height(8.dp)
                                     .fillMaxWidth()
                                     .background(color = Color.Blue)
                             )*/
                        },
                        modifier = Modifier.wrapContentWidth(),
                        edgePadding = 16.dp,
                    ) {
                        allTypesNames.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title) },
                                selected = state.value == index,
                                onClick = {
                                    state.value = index
                                    nameOfSelectedType.value = title
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    createProducts(
                        allProducts =
                        aplicateProductsFilters(
                            allProducts = allProducts,
                            nameOfSelectedType = nameOfSelectedType.value,
                            getAllFilterEspecifications(
                                isVegano = isCheckedVegano,
                                isVegetariano = isCheckedVegetariano,
                                isPescetariano = isCheckedPescetariano,
                                isSinGluten = isCheckedSinGluten,
                                isPicante = isCheckedPicante
                            )
                        ),
                        onValueChangeInformationProduct = onValueChangedInformationProduct,
                        onValueChangeSelectedProduct = onValueChangeSelectedProduct
                    )
                }
            }


        }
    )

}

private fun aplicateProductsFilters(
    allProducts: List<Productos>,
    nameOfSelectedType: String,
    especifications: MutableList<String>
) : MutableList<Productos> {
    var listProducts: MutableList<Productos> = mutableListOf()

    for (i in allProducts) {
        if (i.type.equals(nameOfSelectedType)) listProducts.add(i)
    }
    especifications.forEach{
        listProducts = aplicateEspecificationFilter(it,allProducts = listProducts)
    }
    return listProducts
}

private fun aplicateEspecificationFilter(especification: String, allProducts: MutableList<Productos>) : MutableList<Productos> {
    val products: MutableList<Productos> = mutableListOf()
    allProducts.forEach{ if (it.especifications.contains(especification)) products.add(it) }
    return products
}

@ExperimentalFoundationApi
@Composable
private fun createProducts(
    allProducts: MutableList<Productos>,
    onValueChangeInformationProduct: (Boolean) -> Unit,
    onValueChangeSelectedProduct: (Productos) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp),

    ) {
        for (i in allProducts) {
            item {
                Card(
                    backgroundColor = Color.Red,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .width(50.dp)
                        .height(150.dp),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)

                ) {
                    Button(
                        onClick = {
                            //Producto seleccionado
                            onValueChangeInformationProduct(true)
                            onValueChangeSelectedProduct(i)

                        },
                        modifier = Modifier.pointerInput(Unit){
                            detectTapGestures (
                                onLongPress = {
                                    //Evento al mantener
                                }
                            )
                        },
                        contentPadding = PaddingValues(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier.height(100.dp)
                            ) {
                                Image(
                                    painter =  rememberImagePainter(
                                        data =  if (!i.img.equals("")) "${i.img}" else "https://www.chollosocial.com/media/data/2019/11/678gf34.png",
                                        builder = {
                                            //transformations(CircleCropTransformation())

                                        }
                                    ),
                                    contentDescription ="Imágen del producto",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Box(
                                modifier = Modifier.height(50.dp)
                            ) {
                                Text(
                                    text = i.name,
                                    fontSize = 10.sp,
                                    modifier = Modifier.fillMaxSize(),
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun createAllProductEspecifications(producto: Productos, selectedType: Tipos) {
    var description = rememberSaveable { mutableStateOf("") }
    var numExtra = rememberSaveable { mutableStateOf("") }
    var textQuantity = rememberSaveable{ mutableStateOf("") }

    LazyColumn(
        content = {
            item {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Editar Producto",
                        fontSize = 30.sp,
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterHorizontally
                            )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.height(200.dp)
                        ) {
                            Image(
                                painter =  rememberImagePainter(
                                    data =  if (!producto.img.equals("")) "${producto.img}" else "https://www.chollosocial.com/media/data/2019/11/678gf34.png",
                                    builder = {
                                        //transformations(CircleCropTransformation())

                                    }
                                ),
                                contentDescription ="Imágen del producto",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Inside
                            )
                        }

                        Box(
                            modifier = Modifier.height(50.dp)
                        ) {
                            Text(
                                text = producto.name,
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            IconButton(
                                onClick = {
                                    //Restar 1
                                    numExtra.value = (numExtra.value.toInt() - 1).toString()

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "More icon"
                                )
                            }

                            TextField(
                                value = numExtra.value,
                                onValueChange = {  },
                                label = { Text(it.name) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(PaddingValues(start = 20.dp, end = 20.dp)),
                                enabled = false
                                )

                            IconButton(
                                onClick = {
                                    //Sumar 1
                                    numExtra.value = (numExtra.value.toInt() + 1).toString()
                                }
                            ) {
                                Icon(
                                    Icons.Default.AddCircle,
                                    contentDescription = "More icon"
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

private fun getAllFilterEspecifications(
    isVegano: Boolean,
    isVegetariano: Boolean,
    isPescetariano: Boolean,
    isSinGluten: Boolean,
    isPicante: Boolean
) : MutableList<String>
{
    val especifications: MutableList<String> = mutableListOf()
    if (isVegano || isPescetariano || isPicante || isSinGluten|| isVegetariano) {
        if (isVegano) especifications.add("Vegano")
        if (isVegetariano) especifications.add("Vegetariano")
        if (isPescetariano) especifications.add("Pescetariano")
        if (isSinGluten) especifications.add("Sin Gluten")
        if (isPicante) especifications.add("Picante")
    }

    return  especifications
}

@Composable
private fun LabelledCheckbox(labelText: String,isCheckedValue: Boolean,onValueChangeCheked: (Boolean) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {


        Checkbox(
            checked = isCheckedValue,
            onCheckedChange = { onValueChangeCheked(it) },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Blue)
        )
        Text(
            text = "${labelText}",
            modifier = Modifier
                .clickable{
                    onValueChangeCheked(if (isCheckedValue) false else true)
                }
        )
        Spacer(modifier = Modifier.padding(2.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    IntermodularTheme {
        //MainCreateOrder(1)
    }
}
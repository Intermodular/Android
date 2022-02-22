package sainero.dani.intermodular.Views.Administration.Products

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainViewModelEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainViewModelIngredients
import sainero.dani.intermodular.ViewsItems.*


@Composable
fun MainEditProduct(
    id: Int,
    mainViewModelEspecifications: MainViewModelEspecifications,
    mainViewModelIngredients: MainViewModelIngredients,
    mainViewModelProductos: MainViewModelProducts
) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }
    val (deleteItem, onValueChangeDeleteItem) = remember { mutableStateOf(false) }
    val context = LocalContext.current

    mainViewModelProductos.getProductById(id)
    var ingredientes: MutableList<String> = mutableListOf()
    var especification: MutableList<String> = mutableListOf()

    //Consultas BD
    var selectedProduct: Productos = Productos(0,"","",ingredientes,3.4f, especification,"",1)
    mainViewModelProductos.productListResponse.forEach{
        if (it._id.equals(id))  selectedProduct = it
    }


    var allTypes = mainViewModelProductos.typeListResponse
    var allTypesNames: MutableList<String> = mutableListOf()
    allTypes.forEach{allTypesNames.add(it.name)}


    //Textos
    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf(selectedProduct.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val nameOfNameError: String = "El nombre no puede contener caracteres especiales ni ser mayor de 20"

    var textType = rememberSaveable{mutableStateOf(selectedProduct.type)}
    ingredientes = selectedProduct.ingredients

    var (textCost, onValueChangeCost) = rememberSaveable{mutableStateOf(selectedProduct.price.toString())}
    var (costError,costErrorChange) = remember { mutableStateOf(false) }
    val costOfNameError: String = "El número debe de estar sin ','"

    var (textImg, onValueChangeImg) = rememberSaveable{mutableStateOf(selectedProduct.img)}

    var (textStock, onValueChangeStock) = rememberSaveable{mutableStateOf(selectedProduct.stock.toString())}
    var (stockError,stockErrorChange) = remember { mutableStateOf(false) }
    val stockOfNameError: String = "Debe ser un número y sin ','"

    //Variables de ayuda
    val aplicateState = remember { mutableStateOf(true) }

    if (showToast.value) {
        Toast.makeText(LocalContext.current,textOfToast.value,Toast.LENGTH_SHORT).show()
        showToast.value = false
    }



    if (aplicateState.value) {
        when (mainViewModelEspecifications.especificationsState){
            "New" -> {
                mainViewModelEspecifications._especifications.clear()
                mainViewModelEspecifications._tmpEspecifications.clear()
                selectedProduct.especifications.forEach{ mainViewModelEspecifications.addExtras(it) }
                mainViewModelEspecifications._tmpEspecifications = mainViewModelEspecifications._especifications.toMutableList()
            }
            "Edit" -> {
                mainViewModelEspecifications._especifications = mainViewModelEspecifications._tmpEspecifications.toMutableList()
            }
            "Cancel" -> {
                mainViewModelEspecifications._especifications = mainViewModelEspecifications._tmpEspecifications.toMutableList()
            }
            else  ->{
            }
        }

        when (mainViewModelIngredients.ingredientsState){
            "New" -> {
                mainViewModelIngredients._ingredients.clear()
                mainViewModelIngredients.tmpIngredients.clear()
                mainViewModelIngredients.newsValuesIngredients.clear()
                selectedProduct.ingredients.forEach{ mainViewModelIngredients.addIngredient(it) }
                mainViewModelIngredients.tmpIngredients = mainViewModelIngredients._ingredients.toMutableList()
            }
            "Edit" -> {
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients._ingredients = mainViewModelIngredients.tmpIngredients.toMutableList()
            }
            "Cancel" -> {
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients.tmpIngredients = mainViewModelIngredients._ingredients.toMutableList()
            }
            else  ->{
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients.tmpIngredients = mainViewModelIngredients._ingredients.toMutableList()
            }
        }
        aplicateState.value = false
    }
    especification = mainViewModelEspecifications._especifications.toMutableList()
    ingredientes = mainViewModelIngredients._ingredients.toMutableList()

    if (deleteItem) {
        var title: String = "¿Seguro que desea eliminar el producto seleccionado?"
        var subtitle: String = "No podrás volver a recuperarlo"

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
        ) {
            if (it) {
                mainViewModelProductos.deleteProduct(id = id)
                Toast.makeText(context,"El producto se ha eliminado correctamente",Toast.LENGTH_SHORT).show()
                navController.popBackStack()

            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de productos",color = Color.White)
                },
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(onClick = {
                        onValueChangeDeleteItem(true)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color.White
                        )
                    }

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
                }
            )

        },
        content = {
            LazyColumn(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = {
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp, 4.dp)
                                    .fillMaxWidth(0.5f)
                                    .height(150.dp),
                                shape = RoundedCornerShape(200.dp),
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = selectedProduct.img,
                                        builder = {
                                            scale(Scale.FIT)
                                            transformations(CircleCropTransformation())
                                            crossfade(true)
                                        },
                                    ),
                                    contentDescription = "Imagen del producto ${selectedProduct.name}",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                    }
                    item {
                        createRowListWithErrorMesaje(
                            text = "Nombre",
                            value = textName,
                            onValueChange = onValueChangeName,
                            validateError = mainViewModelProductos::isValidNameOfProduct,
                            errorMesaje = nameOfNameError,
                            changeError = nameErrorChange,
                            error = nameError,
                            mandatory = true,
                            KeyboardType = KeyboardType.Text
                        )
                    }
                    item {
                        textType.value = selectedDropDownMenu("Tipo",allTypesNames)
                    }

                    item {
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        dropDownMenuWithNavigation(
                            text ="Ingredientes",
                            suggestions = ingredientes,
                            onClick = { onClickIngredients(id) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        createRowListWithErrorMesaje(
                            text = "Coste",
                            value = textCost,
                            onValueChange = onValueChangeCost,
                            validateError = mainViewModelProductos::isValidCostOfProduct,
                            errorMesaje = costOfNameError,
                            changeError = costErrorChange,
                            error = costError,
                            mandatory = true,
                            KeyboardType = KeyboardType.Number
                        )
                    }

                    item {
                        dropDownMenuWithNavigation(
                            text = "Especificaciones",
                            suggestions = especification,
                            onClick = { onClickEspecifications(id)}
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        createRowList(
                            text = "Imágen",
                            value = textImg,
                            onValueChange = onValueChangeImg,
                            enable = true,
                            KeyboardType = KeyboardType.Text
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        createRowListWithErrorMesaje(
                            text = "Stock",
                            value = textStock,
                            onValueChange = onValueChangeStock,
                            validateError = mainViewModelProductos::isValidStockOfProduct,
                            errorMesaje = stockOfNameError,
                            changeError = stockErrorChange,
                            error = stockError,
                            mandatory = false,
                            KeyboardType = KeyboardType.Number
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 15.dp, end = 10.dp)
                        ) {
                            Button(
                                onClick = {
                                    onValueChangeName(selectedProduct.name)
                                    onValueChangeImg(selectedProduct.img)
                                    onValueChangeCost(selectedProduct.price.toString())
                                    onValueChangeStock(selectedProduct.stock.toString())
                                    textType.value = selectedProduct.type
                                    mainViewModelEspecifications._especifications.clear()
                                    mainViewModelIngredients._ingredients.clear()

                                },
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 20.dp)
                            ) {
                                Text(text = "Revertir cambios", fontSize = 15.sp)
                            }

                            Button(
                                onClick = {
                                    //Guardar los cambios en la BD
                                    if (mainViewModelProductos.checkAllValidations(
                                            textName = textName,
                                            textPrice = textCost,
                                            textStock = textStock
                                        )
                                    ) {
                                        val updateProduct: Productos = Productos(
                                            _id = id,
                                            name = textName,
                                            type =  textType.value,
                                            ingredients = mainViewModelIngredients._ingredients,
                                            price =  textCost.toFloat(),
                                            especifications = mainViewModelEspecifications._especifications,
                                            img = textImg,
                                            stock =  textStock.toInt()
                                        )
                                        mainViewModelProductos.editProduct(product = updateProduct)
                                        showToast.value = true
                                        textOfToast.value = "El producto se ha actualizado correctamente"
                                        navController.popBackStack()
                                    } else {
                                        showToast.value = true
                                        textOfToast.value = "Debes de rellenar todos los campos correctamente"
                                    }
                                },
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                            ) {
                                Text(text = "Guardar cambios", fontSize = 15.sp)
                            }
                        }
                    }
                }
            )
        }
    )

}

private fun onClickIngredients(id:Int){
    navController.navigate("${Destinations.Ingredient.route}/${id}")
}

private fun onClickEspecifications(id: Int) {
    navController.navigate("${Destinations.Especifications.route}/${id}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview11() {
    //MainEditProduct("")
}


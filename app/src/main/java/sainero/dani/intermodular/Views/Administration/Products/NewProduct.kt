package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainViewModelEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainViewModelIngredients
import java.util.regex.Pattern
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.dropDownMenuWithNavigation
import sainero.dani.intermodular.ViewsItems.selectedDropDownMenu

@Composable
fun MainNewProduct(
    mainViewModelEspecifications: MainViewModelEspecifications,
    mainViewModelIngredients: MainViewModelIngredients,
    mainViewModelProductos: MainViewModelProducts
) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    //Textos
    var allTypes = mainViewModelProductos.typeListResponse
    var allTypesNames: MutableList<String> = mutableListOf()
    allTypes.forEach{allTypesNames.add(it.name)}


    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf("") }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val nameOfNameError: String = "El nombre no puede contener caracteres especiales ni ser mayor de 20"

    var textType = rememberSaveable{mutableStateOf(allTypesNames[0])}

    var ingredientes: MutableList<String> = mutableListOf()
    var especification: MutableList<String> = mutableListOf()

    var (textCost, onValueChangeCost) = rememberSaveable{ mutableStateOf("0") }
    var (costError,costErrorChange) = remember { mutableStateOf(false) }
    val costOfNameError: String = "Debe ser un número y sin ','"

    var (textImg, onValueChangeImg) = rememberSaveable{ mutableStateOf("") }

    var (textStock, onValueChangeStock) = rememberSaveable{ mutableStateOf("0") }
    var (stockError,stockErrorChange) = remember { mutableStateOf(false) }
    val stockOfNameError: String = "Debe ser un número entero"


    //Variables de ayuda
    val aplicateState = remember { mutableStateOf(true) }
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }

    if (showToast.value) {
        Toast.makeText(LocalContext.current,textOfToast.value,Toast.LENGTH_SHORT).show()
        showToast.value = false
    }

    if (aplicateState.value) {
        when (mainViewModelEspecifications.especificationsState){
            "New" -> {
                mainViewModelEspecifications._especifications.clear()
                mainViewModelEspecifications._tmpEspecifications.clear()
                //selectedProduct.especifications.forEach{ mainViewModelEspecifications.addExtras(it) }
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
                mainViewModelIngredients._tmpIngredients.clear()
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients._tmpIngredients = mainViewModelIngredients._ingredients.toMutableList()
            }
            "Edit" -> {
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients._ingredients = mainViewModelIngredients._tmpIngredients.toMutableList()
            }
            "Cancel" -> {
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients._tmpIngredients = mainViewModelIngredients._ingredients.toMutableList()
            }
            else  ->{
                mainViewModelIngredients.newsValuesIngredients.clear()
                mainViewModelIngredients._tmpIngredients = mainViewModelIngredients._ingredients.toMutableList()

            }
        }
        aplicateState.value = false
    }
    especification = mainViewModelEspecifications._especifications.toMutableList()
    ingredientes = mainViewModelIngredients._ingredients.toMutableList()

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


                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            GlobalVariables.navController.popBackStack()
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

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.9f),

                    content = {
                        item {
                            Spacer(modifier = Modifier.padding(10.dp))
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
                            dropDownMenuWithNavigation(
                                text = "Ingredientes",
                                suggestions = mainViewModelIngredients._ingredients,
                                navigate = "${Destinations.Ingredient.route}/${0}"
                            )
                        }
                        item {
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
                                suggestions = mainViewModelEspecifications._especifications,
                                navigate = "${Destinations.Especifications.route}/${0}"
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                            createRowList(
                                text = "Imágen",
                                value = textImg,
                                onValueChange =  onValueChangeImg,
                                enable = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
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
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            textName = ""
                            textType.value = ""
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
                        Text(text = "Resetear campos", fontSize = 15.sp)
                    }

                    Button(
                        onClick = {

                            if (mainViewModelProductos.checkAllValidations(
                                    textName = textName,
                                    textPrice = textCost,
                                    textStock = textStock
                                )
                            ) {
                                val product: Productos
                                product = Productos(
                                    _id = 0,
                                    name = textName,
                                    type = textType.value,
                                    ingredients = ingredientes,
                                    price = textCost.toFloat(),
                                    especifications = mainViewModelEspecifications._especifications,
                                    img = textImg,
                                    stock = textStock.toInt()
                                )
                                mainViewModelProductos.uploadProduct(product = product)
                                showToast.value = true
                                textOfToast.value = "El producto se creado correctamente"
                                navController.popBackStack()
                            }
                            else {
                                showToast.value = true
                                textOfToast.value = "Debes de rellenar todos los campos correctamente"
                            }

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
                        Text(text = "Crear producto", fontSize = 15.sp)
                    }

                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview12() {
        //MainNewProduct()
}
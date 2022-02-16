package sainero.dani.intermodular.Views.Administration.Products.Ingredients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.internal.assertThreadDoesntHoldLock
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainIngredient(
    _id: Int,
    viewModelProductos: ViewModelProductos,
    mainViewModelIngredients: MainViewModelIngredients
) {


    val (refresh, onValueChangeRefresh) = remember { mutableStateOf(true)}
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))




    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = " Edición de Ingredientes")
                },
                actions = {

                }
            )
        },
        content = {
            if (refresh)
                createContent(
                    onValueChangeRefresh = onValueChangeRefresh,
                    _id = _id,
                    viewModelProductos = viewModelProductos,
                    mainViewModelIngredients = mainViewModelIngredients
                )
        }
    )

}

@Composable
private fun createContent(
    mainViewModelIngredients: MainViewModelIngredients,
    _id: Int,
    viewModelProductos: ViewModelProductos,
    onValueChangeRefresh: (Boolean) -> Unit
) {

    var selectedProduct : Productos

    //Textos
    var (error,errorChange) = remember { mutableStateOf(false) }
    val errorMesaje: String = "No puede contener números o caracteres especiales, máximo 14 dígitos"

  //Varaibles de ayuda
    var getValues = remember { mutableStateOf(false)}

    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        mainViewModelIngredients._tmpIngredients.forEach{ suggestion ->


            item {
                var selectedText by remember { mutableStateOf(suggestion) }
                Spacer(modifier = Modifier.padding(2.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                ) {

                    OutlinedTextField(
                        value =  selectedText,
                        onValueChange = {
                            selectedText = it
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                            .fillMaxWidth(),
                        placeholder = { Text(text = "Ingrediente")},
                        isError = error,
                        leadingIcon = {
                            IconButton(
                                onClick = {

                                    mainViewModelIngredients._tmpIngredients.remove(suggestion)
                                    onValueChangeRefresh(false)
                                    onValueChangeRefresh(true)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete suggestion",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        }
                    )
                    val assistiveElementText = if (error) errorMesaje else ""
                    val assistiveElementColor = if (error) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                    }
                    Text(
                        text = assistiveElementText,
                        color = assistiveElementColor,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 10.dp, end = 20.dp)
                    )
                }
                if (getValues.value) {

                    mainViewModelIngredients.newsValuesIngredients.add(selectedText)
                    if (mainViewModelIngredients._ingredients.size  == mainViewModelIngredients.newsValuesIngredients.size) {
                        mainViewModelIngredients._tmpIngredients = mainViewModelIngredients.newsValuesIngredients.toMutableList()
                        mainViewModelIngredients.ingredientsState = "Edit"
                        getValues.value = false
                        navController.popBackStack()
                    }
                }
            }

        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        mainViewModelIngredients.addTmpIngredients("")
                        onValueChangeRefresh(false)
                        onValueChangeRefresh(true)
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
                        .fillMaxWidth()
                ) {
                    Text(text = "Añadir ingrediente", fontSize = 15.sp)
                }
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                    Button(
                        onClick = {
                            mainViewModelIngredients.ingredientsState = "Cancel"
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
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        Text(text = "Revertir cambios")
                    }

                    Button(
                        onClick = {
                            //Guardar los cambios en la BD
                            //getIngredients.value = true
                            onValueChangeRefresh(false)
                            onValueChangeRefresh(true)
                            getValues.value = true

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

                        Text(text = "Guardar cambios", fontSize = 15.sp)
                    }
                }

        }
    }

}




//Validaciones
private fun isValidIngredient(text: String) = Pattern.compile("^[a-zA-Z]{1,14}$", Pattern.CASE_INSENSITIVE).matcher(text).find()


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //MainIngredient("Android")
}
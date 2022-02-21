package sainero.dani.intermodular.Views.Administration.Products.Ingredients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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

//Solucionar error de ingredientes menor al q ya hay
@Composable
fun MainIngredient(
    mainViewModelIngredients: MainViewModelIngredients
) {
    val (refresh, onValueChangeRefresh) = remember { mutableStateOf(true)}
    val localTmpIngredients = remember  { mutableStateListOf<Extras>()}
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = " Edición de Ingredientes")
                },
            )
        },
        content = {
            if (refresh)
                createContent(
                    onValueChangeRefresh = onValueChangeRefresh,
                    mainViewModelIngredients = mainViewModelIngredients
                )
        }
    )

}

@Composable
private fun createContent(
    mainViewModelIngredients: MainViewModelIngredients,
    onValueChangeRefresh: (Boolean) -> Unit
) {


    //Textos
    var (error,errorChange) = remember { mutableStateOf(false) }
    val errorMesaje: String = "No puede contener números o caracteres especiales, máximo 14 dígitos"

    //Varaibles de ayuda
    var getValues = remember { mutableStateOf(false)}

    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        itemsIndexed(mainViewModelIngredients.tmpIngredients) { index, suggestion ->
            var selectedText = remember { mutableStateOf(suggestion) }
            selectedText.value = suggestion
            Spacer(modifier = Modifier.padding(2.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                OutlinedTextField(
                    value =  selectedText.value,
                    onValueChange = {
                        selectedText.value = it
                        mainViewModelIngredients.tmpIngredients[index] = selectedText.value
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                        .fillMaxWidth(),
                    placeholder = { Text(text = "Ingrediente")},
                    isError = error,
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                mainViewModelIngredients.tmpIngredients.removeAt(index = index)
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
            }
        }


        item {
            Spacer(modifier = Modifier.padding(6.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, end = 10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            mainViewModelIngredients.tmpIngredients.add("")
                            onValueChangeRefresh(false)
                            onValueChangeRefresh(true)
                        },
                    ) {
                        Text(text = "Añadir ingrediente", fontSize = 15.sp)
                    }
                }
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, end = 10.dp)
                ) {
                    Button(
                        onClick = {
                            mainViewModelIngredients.ingredientsState = "Cancel"
                            navController.popBackStack()
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
                        Text(text = "Revertir cambios")
                    }

                    Button(
                        onClick = {
                            //Guardar los cambios en la BD
                            onValueChangeRefresh(false)
                            onValueChangeRefresh(true)
                            mainViewModelIngredients.ingredientsState = "Edit"
                            navController.popBackStack()
                        },
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                    ) {
                        Text(text = "Guardar cambios")
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //MainIngredient("Android")
}
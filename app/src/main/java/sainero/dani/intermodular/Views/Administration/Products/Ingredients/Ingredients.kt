package sainero.dani.intermodular.Views.Administration.Products.Ingredients

import android.os.Bundle
import android.support.v4.os.IResultReceiver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.internal.assertThreadDoesntHoldLock
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.Views.Administration.Employee.ToastDemo
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainIngredient(_id: Int, viewModelProductos: ViewModelProductos) {
    val getIngredients = remember { mutableStateOf(false)}
    val saveIngredient = remember { mutableStateOf(false)}
    val newIngredient = remember { mutableStateOf(false)}
    val deleteIngredient = remember { mutableStateOf(false)}
    val nameOfDeleteIngredient = remember { mutableStateOf("")}
    var selectedProduct : Productos

    //Textos
    var (error,errorChange) = remember { mutableStateOf(false) }
    val errorMesaje: String = "No puede contener números o caracteres especiales, máximo 14 dígitos"

    //Consulta BD
    selectedProduct = viewModelProductos.product


    var suggestions: MutableList<String> = remember { selectedProduct.ingredients }
    var listOfText: MutableList<String> = remember { mutableListOf() }

    //Funciones auxiliares
    if(newIngredient.value) {
        suggestions.add("New ingredient")
        newIngredient.value = false
    }

    if (deleteIngredient.value) {
        suggestions.remove(nameOfDeleteIngredient.value)
        deleteIngredient.value = false
    }

    if (saveIngredient.value) {
        selectedProduct.ingredients = listOfText
            updateProductInBd(
                product = selectedProduct,
                viewModelProductos = viewModelProductos,
                _id = _id
            )
        getIngredients.value = false
        saveIngredient.value = false

    }
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        suggestions.forEach{ suggestion ->
            item {
                var selectedText by remember { mutableStateOf("") }
                Spacer(modifier = Modifier.padding(2.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    OutlinedTextField(
                        value =  selectedText,
                        onValueChange = {
                            selectedText = it
                            //errorChange(!isValidIngredient(it))

                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                            .fillMaxWidth(),
                        placeholder = { Text(text = suggestion)},
                        isError = error,
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    deleteIngredient.value = true
                                    nameOfDeleteIngredient.value = suggestion
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

                if(getIngredients.value) {
                    if (selectedText.equals("")) selectedText = suggestion
                    if (!selectedText.equals("New ingredient") && listOfText.size <= suggestion.length)  listOfText.add(selectedText)
                    saveIngredient.value = true

                }
            }
        }

        item {
            Spacer(modifier = Modifier.padding(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = {
                        newIngredient.value = true
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
                    Text(text = "Añadir ingrediente", fontSize = 15.sp)
                }

                Button(
                    onClick = {
                        //Guardar los cambios en la BD
                            getIngredients.value = true

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

private fun updateProductInBd(product: Productos,viewModelProductos: ViewModelProductos,_id: Int) {
    viewModelProductos.editProduct(product)
    navController.navigate("${Destinations.EditProduct.route}/${_id}")
}

private fun createProductInBd(product: Productos,viewModelProductos: ViewModelProductos,_id: Int) {
    viewModelProductos.uploadProduct(product)
    navController.navigate("${Destinations.EditProduct.route}/${_id}")
}

private fun ingredient() {

}

//Validaciones
private fun isValidIngredient(text: String) = Pattern.compile("^[a-zA-Z]{1,14}$", Pattern.CASE_INSENSITIVE).matcher(text).find()


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //MainIngredient("Android")
}
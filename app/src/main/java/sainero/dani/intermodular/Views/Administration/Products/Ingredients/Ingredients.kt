package sainero.dani.intermodular.Views.Administration.Products.Ingredients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.internal.assertThreadDoesntHoldLock
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Views.Administration.Employee.ToastDemo
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.ui.theme.IntermodularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@Composable
fun MainIngredient(_id: Int) {
    val addIngredient = remember{ mutableStateOf(false)}
    val newIngredient = remember{ mutableStateOf(false)}

    //Consulta BD
    var suggestions: MutableList<String> = remember { mutableListOf("Pan", "Tomate","Picante","Lechuga")}


    var listOfText: MutableList<String> = mutableListOf()
    if(newIngredient.value) {
        suggestions.add("New ingredient")
        newIngredient.value = false
    }
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        suggestions.forEach{ suggestion ->
            item {
                var selectedText by remember { mutableStateOf("") }
                Spacer(modifier = Modifier.padding(2.dp))
                Row() {
                    OutlinedTextField(
                        value =  selectedText,
                        onValueChange = { selectedText = it },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                            .fillMaxWidth(),
                        placeholder = { Text(text = suggestion)}
                    )
                }
                if(addIngredient.value) {
                    if (selectedText.equals("")) selectedText = suggestion
                    if (!selectedText.equals("New ingredient"))  listOfText.add(selectedText)
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
                   /* Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir ingrediente",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))*/
                    Text(text = "Añadir ingrediente", fontSize = 15.sp)
                }

                if(addIngredient.value) ToastDemo(listOfText[listOfText.size-1])

                Button(
                    onClick = {
                        //Guardar los cambios en la BD y ir atras

                        //if(addIngredient.value) navController.navigate("${Destinations.EditProduct.route}/${_id}")
                        addIngredient.value = true

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
                   /* Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Guardar cambios",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))*/
                    Text(text = "Guardar cambios", fontSize = 15.sp)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntermodularTheme {

        //MainIngredient("Android")
    }
}
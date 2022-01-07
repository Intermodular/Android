package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Views.Administration.Products.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.NavigationHost
import kotlin.random.Random


var testText: MutableList<String> = mutableListOf()

@ExperimentalFoundationApi
class EditProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {
               NavigationHost()
            }
        }
    }
}

@Composable
fun MainEditProduct(navController: NavController, id: String) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }

    //Esto se eliminará por una consulta a la BD
    var selectedProduct: Productos
    val allProducts: MutableList<Productos> = mutableListOf()
    for(i in 1..50) allProducts.add(Productos(i.toString(),"Product ${i}","Comida", mutableListOf<String>("ingrediente1", "ingrediente2"),1.5f,mutableListOf<String>("Vegano", "Gluten"),"rutaImg",1))

    //Esto es una busqueda en la BD
    for(i in allProducts)
        if(i._id.equals(id))
            selectedProduct = i

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Productos",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    Box (Modifier.wrapContentSize()){
                        IconButton(onClick = {
                            expanded.value = true
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "Localized description"
                            )
                        }

                        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false

                                }) {
                                Text(text = "Eliminar producto")
                            }
                            Divider()
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                }) {
                                Text(text = "Gestionar tipos")
                            }
                        }
                    }

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
                createRowList("Nombre")
                createRowList("Tipo")
                createRowList("Ingredientes")

            }
        }
    )

}

@Composable
fun createRowList(text: String) {
    var textProduct  by rememberSaveable { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = textProduct,
            onValueChange = {
                textProduct = it
            },
            placeholder = { Text(text) },
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
        )
    }
    testText.add(textProduct)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview11() {
    IntermodularTheme {
        val navController = rememberNavController()
        MainEditProduct(navController,"")
    }
}
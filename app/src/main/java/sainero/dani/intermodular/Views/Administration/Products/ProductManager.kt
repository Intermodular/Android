package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Views.Administration.Products.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.Destinations
import sainero.dani.intermodular.navigation.NavigationHost

@ExperimentalFoundationApi
class ProductManager : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {
                NavigationHost()
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainProductManager(navController: NavController) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    var allProducts: MutableList<Productos> = mutableListOf()

    for(i in 1..50) allProducts.add(Productos(i.toString(),"Product ${i}","Comida", mutableListOf<String>("ingrediente1", "ingrediente2"),1.5f,mutableListOf<String>("Vegano", "Gluten"),"rutaImg",1))
    var allproductsFilter: MutableList<Productos> = mutableListOf()

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


                }
            )

        },
        drawerContent = {
            Text(text = "Filtro1")
            Text(text = "Filtro2")
            Text(text = "Filtro3")
        },
        content = {
            for(i in allProducts) if(i._id.toInt() >= 10 && i._id.toInt() <= 20) allproductsFilter.add(i)
            creteProductList(allproductsFilter, navController)

        },
        //Preguntar sobre si quieren agregar un producto así
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //Ir a nuevo producto¿?
                }
            ) {
                Text("+")
            }
        }
    )
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview10() {
    IntermodularTheme {
        val navController = rememberNavController()
        MainProductManager(navController)
    }
}



@Composable
@ExperimentalFoundationApi
fun creteProductList(listProducts: MutableList<Productos>, navController: NavController) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ){
        for( i in listProducts) {
            item {
                Box (Modifier.padding(10.dp)) {
                    Button(
                        onClick = {
                            navController.navigate("${Destinations.EditProduct.route}/${i._id}")
                        },
                        contentPadding = PaddingValues(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Blue
                        )
                    ) {
                        Text(text = i.nombre)
                    }
                }
            }
        }
    }
}
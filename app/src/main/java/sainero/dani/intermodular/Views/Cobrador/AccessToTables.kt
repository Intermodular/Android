package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.solver.state.State
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.Destinations
import sainero.dani.intermodular.navigation.NavigationHost
@ExperimentalFoundationApi

class AccessToTables : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationHost()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainAccessToTables(navController: NavController) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var allTables: MutableList<Mesas> = mutableListOf()
    for(i in 1..150)
        allTables.add(Mesas("mesa" + i ,4,"terraza"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            scaffoldState = scaffoldState,

            //Preguntar como cojones hacemos el menú
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = {
                                    navController.navigate(Destinations.Login.route)

                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Administrar", fontSize = 10.sp)
                            }


                            Button(
                                onClick = {
                                    navController.navigate(Destinations.Login.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color.Blue
                                )
                            ) {

                                Text(text = "Gestionar Reservas", fontSize = 10.sp)
                            }


                            Button(
                                onClick = {
                                    navController.navigate(Destinations.Login.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color.Blue
                                )
                            ) {

                                Text(text = "Cerrar sesión", fontSize = 10.sp)
                            }
                        }
                    },
                    backgroundColor = Color.Blue,
                )

             },
           /* floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = { FloatingActionButton(onClick = {}){
                Text("X")
            } },*/
            drawerContent = {
                Column() {
                    Text(text = "Filtros")
                    Text(text = "Filtros")
                    Text(text = "Filtros")
                    Text(text = "Filtros")
                }
            },
            content = {
                //val scrollState = rememberScrollState()

                LazyVerticalGrid(
                    cells = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
                ) {
                    for (i in allTables) {
                        item {
                            Box (Modifier.padding(10.dp)) {
                                Button(
                                    onClick = {
                                        navController.navigate(Destinations.CreateOrder.route + "/${i.nombre}")
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
          },
          //  bottomBar = { BottomAppBar(backgroundColor = Color.Blue) { Text("BottomAppBar") } }
        )

    }
}

fun createTables() {

}


@Preview(showBackground = true)
@ExperimentalFoundationApi
@Composable
fun DefaultPreview3() {
    val navController = rememberNavController()
    MainAccessToTables(navController)
}
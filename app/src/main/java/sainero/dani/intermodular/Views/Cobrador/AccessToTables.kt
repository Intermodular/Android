package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelMesas

@ExperimentalFoundationApi

class AccessToTables : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainAccessToTables(viewModelMesas: ViewModelMesas) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    var allTables: List<Mesas> = viewModelMesas.mesasListResponse

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
                        Text(text = "Menú de mesas", color = Color.White)
                    },
                    backgroundColor = Color.Blue,
                    elevation = AppBarDefaults.TopAppBarElevation,
                    actions = {
                        Box (Modifier.wrapContentSize()){
                            IconButton(onClick = {
                                expanded.value = true
                                result.value = "More icon clicked"
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
                                        GlobalVariables.navController.navigate(Destinations.AccessToTables.route)
                                    }) {
                                    Text(text = "Gestionar Reservas")
                                }
                                Divider()
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                        GlobalVariables.navController.navigate(Destinations.MainAdministrationActivity.route)
                                    }) {
                                    Text(text = "Entrar como Administrador")
                                }
                                Divider()
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                        val navBuilder: NavOptionsBuilder


                                        GlobalVariables.navController.navigate(Destinations.Login.route){
                                            popUpTo(0)
                                        }

                                    }) {
                                    Text(text = "Cerrar sesión")
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        // show drawer icon
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "")
                        }
                    },

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
                                        GlobalVariables.navController.navigate(Destinations.CreateOrder.route + "/${i._id}")
                                      },
                                    contentPadding = PaddingValues(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White,
                                        contentColor = Color.Blue
                                    )
                                ) {
                                    Text(text = i.zone)
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
    //MainAccessToTables()
}
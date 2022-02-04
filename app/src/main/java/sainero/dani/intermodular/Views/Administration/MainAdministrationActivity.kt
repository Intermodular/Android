package sainero.dani.intermodular.Views.Administration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables

@ExperimentalFoundationApi

class MainAdministrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //No se ejecuta
        }
    }
}



@Composable
fun MainAdministrationActivityView() {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }

    val navController = GlobalVariables.navController

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Administración", color = Color.White)
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
                                    navController?.navigate(Destinations.AccessToTables.route)
                                }) {
                                    Text(text = "Entrar como cobrador")
                                }
                            Divider()
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                    val navBuilder: NavOptionsBuilder


                                    navController.navigate(Destinations.Login.route){
                                        popUpTo(0)
                                    }

                                }) {
                                Text(text = "Cerrar sesión")
                            }
                            }
                        }
                    }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(20.dp))

                Image(
                    painter = painterResource(id = sainero.dani.intermodular.R.drawable.logo_eros),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(300.dp)
                        .width(500.dp)
                        .padding(20.dp),
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        navController?.navigate(Destinations.EmployeeManager.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Gestionar Empleados",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                    Text(text = "Gestionar Empleados", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        navController?.navigate(Destinations.ZoneManager.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Gestionar Zonas/Mesas",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                    Text(text = "Gestionar Zonas/Mesas", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        navController?.navigate(Destinations.ProductManager.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Gestionar Productos",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                    Text(text = "Gestionar Productos", fontSize = 20.sp)
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    val navController = rememberNavController()
    MainAdministrationActivityView()
}
package sainero.dani.intermodular.Views.Administration.Employee

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.Destinations
import sainero.dani.intermodular.navigation.NavigationHost
@ExperimentalFoundationApi
class EmployeeManager : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            var  textNameUser by rememberSaveable { mutableStateOf("") }
            IntermodularTheme {
               NavigationHost()
            }
        }
    }
}

@Composable
fun MainEmployeeManager(navController: NavController) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var selectedUser: Users = Users("0","","","","","","","","admin")

    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }


    //Eliminar
    var allUsers: MutableList<Users> = mutableListOf()
    for(i in 1..20)  allUsers.add(Users(i.toString(),"49760882Z" ,"empleado" + i,"x","x","x","x","x","admin"))
    ///

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
                            Text(text = "Lista de Empleados",color = Color.White)
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

                                    }) {
                                    Text(text = "Gestionar todas las nóminas")
                                }
                                Divider()
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                    }) {
                                    Text(text = "Gestionar nomina del empleado seleccionado")
                                }
                            }
                        }
                    }

//Test
                )

            },
            drawerContent = {
               Text(text = "Filtros")
               Text(text = "Filtros")
               Text(text = "Filtros")
            },
            content = {


                LazyColumn(
                    contentPadding = PaddingValues()
                ) {
                    for (i in allUsers) {
                        item {
                            Row (
                                Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        selectedUser = i
                                    }) {

                                Text(text = i.nombre)
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(text = i.dni)
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(text = i.rol)
                            }
                        }
                    }
                    item {


                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("${Destinations.EditEmployee.route}/${selectedUser._id}")
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
                                //Preguntar porque no funciona correctamente esta opción
                                enabled = selectedUser._id !== "0",
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar Usuario",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                                Text(text = "Editar Empleado", fontSize = 15.sp)
                            }


                            Button(
                                onClick = {
                                    navController.navigate(Destinations.CreateEmployee.route)
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
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Nuevo Usuario",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                                Text(text = "Nuevo Empleado", fontSize = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                    }
                }

            },

             /* bottomBar = { BottomAppBar(backgroundColor = Color.Blue) { Text("BottomAppBar") } }*/
        )

    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    val navController = rememberNavController()
    MainEmployeeManager(navController)
}
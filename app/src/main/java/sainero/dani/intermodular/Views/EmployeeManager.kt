package sainero.dani.intermodular.Views

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
    var textDniUser by rememberSaveable { mutableStateOf("") }
    var textNameUser by rememberSaveable { mutableStateOf("") }

    var textSurnameUser by rememberSaveable { mutableStateOf("") }
    var textFnacUser by rememberSaveable { mutableStateOf("") }
    var textUserUser by rememberSaveable { mutableStateOf("") }
    var textPasswordUser by rememberSaveable { mutableStateOf("") }
    var textEmailUser by rememberSaveable { mutableStateOf("") }
    var textRolUser by rememberSaveable { mutableStateOf("") }
    var selectedUser: Users = Users("","","","","","","","")

    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }


    //Eliminar
    var allUsers: MutableList<Users> = mutableListOf()
    for(i in 1..20)
        allUsers.add(
            Users("49760882Z" ,"empleado" + i,"x","x","x","x","x","admin"))

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
                        /*
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = {
                                    navController.navigate(Destinations.Login.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color.Blue
                                )
                            ) {

                                Text(text = "Gestionar Nóminas", fontSize = 10.sp)
                            }
                        }*/
                        /*
                        Text(text = "Gestionar Nóminas", fontSize = 40.sp, modifier = Modifier.clickable {
                            navController.navigate(Destinations.Login.route)

                        })
                        */
                            Text(text = "Gestion de empleados",color = Color.White)

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


                )

            },
            drawerContent = {
                LazyColumn(
                    contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
                ) {
                    item {
                        Text(
                            text = "Lista de Empleados",
                            color = Color.Blue,
                            fontSize = 25.sp
                        )
                    }
                    for (i in allUsers) {
                        item {
                            Row (
                                Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        selectedUser = Users(
                                            i.dni,
                                            i.nombre,
                                            i.apellido,
                                            i.fnac,
                                            i.email,
                                            i.user,
                                            i.passwrd,
                                            i.rol
                                        )
                                        textDniUser = selectedUser.dni
                                        textNameUser = selectedUser.nombre
                                        textSurnameUser = selectedUser.apellido
                                        textFnacUser = selectedUser.fnac
                                        textEmailUser = selectedUser.email
                                        textUserUser = selectedUser.user
                                        textPasswordUser = selectedUser.passwrd
                                        textRolUser = selectedUser.rol
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
                        Spacer(modifier = Modifier.padding(5.dp))
                        Button(
                            onClick = {


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
                                contentDescription = "Nuevo empleado",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                            Text(text = "Nuevo empleado", fontSize = 15.sp)
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    
                }


            },
            content = {
                //val scrollState = rememberScrollState()
                val createUser = allUsers.get(1)
                Column(
                    Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(text = "DNI:", Modifier.width(100.dp))
                            OutlinedTextField(
                                value = textDniUser,
                                onValueChange = {
                                    textDniUser = it
                                },
                                placeholder = { Text("DNI") },
                                label = { Text(text = "DNI") },
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 20.dp)
                            )
                        }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "Nombre:", Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textNameUser,
                            onValueChange = {
                                textNameUser = it
                            },
                            placeholder = { Text("Nombre") },
                            label = { Text(text = "Nombre") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                        Text(text = "Apellido:", Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textSurnameUser,
                            onValueChange = {
                                textSurnameUser = it
                            },
                            placeholder = { Text("Apellido") },
                            label = { Text(text = "Apellido") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "Fecha de nacimiento:" , Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textFnacUser,
                            onValueChange = {
                                textFnacUser = it
                            },
                            placeholder = { Text("User") },
                            label = { Text(text = "Fecha de nacimiento") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)

                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "Usuario:", Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textUserUser,
                            onValueChange = {
                                textUserUser = it
                            },
                            placeholder = { Text("User") },
                            label = { Text(text = "User") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "Password:", Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textPasswordUser,
                            onValueChange = {
                                textPasswordUser = it
                            },
                            placeholder = { Text("Password") },
                            label = { Text(text = "Password") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)
                        )
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "Mail:", Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textEmailUser,
                            onValueChange = {
                                textEmailUser = it
                            },
                            placeholder = { Text("Mail") },
                            label = { Text(text = "Mail") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)
                        )
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "Rol:", Modifier.width(100.dp))
                        OutlinedTextField(
                            value = textRolUser,
                            onValueChange = {
                                textRolUser = it
                            },
                            placeholder = { Text("Rol") },
                            label = { Text(text = "Rol") },
                            modifier = Modifier
                                .padding(start = 10.dp, end = 20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Button(
                            onClick = {
                                textDniUser = selectedUser.dni
                                textNameUser = selectedUser.nombre
                                textSurnameUser = selectedUser.apellido
                                textFnacUser = selectedUser.fnac
                                textEmailUser = selectedUser.email
                                textUserUser = selectedUser.user
                                textPasswordUser = selectedUser.passwrd
                                textRolUser = selectedUser.rol
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
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Revertir cambios",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                            Text(text = "Revertir cambios", fontSize = 15.sp)
                        }



                        Button(
                            onClick = {
                                selectedUser = Users(textDniUser, textNameUser, textSurnameUser, textFnacUser, textEmailUser, textUserUser, textPasswordUser, textRolUser)
                                //actualizar en la BD con este objeto

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
                                imageVector = Icons.Default.Done,
                                contentDescription = "Guardar cambios",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                            Text(text = "Guardar cambios", fontSize = 15.sp)
                        }

                    }
                }
            },

            //  bottomBar = { BottomAppBar(backgroundColor = Color.Blue) { Text("BottomAppBar") } }
        )

    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    val navController = rememberNavController()
    MainEmployeeManager(navController)
}
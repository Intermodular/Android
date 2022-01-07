package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
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
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Views.Administration.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.Destinations
import sainero.dani.intermodular.navigation.NavigationHost

@ExperimentalFoundationApi
class EditEmployee : ComponentActivity() {
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
fun MainEditEmployee(navController: NavController, id: String) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }



    //Test

    var selectedUser: Users = Users("2","","","","","","","","admin")
    var allUsers: MutableList<Users> = mutableListOf()
    for(i in 1..20)  allUsers.add(Users(i.toString(),"49760882Z" ,"empleado" + i,"x","x","x","x","x","admin"))

    //Esto es una busqueda en la BD
   for(i in allUsers)
       if(i._id.equals(id))
           selectedUser = i


    var textDniUser by rememberSaveable { mutableStateOf(selectedUser.dni) }
    var textNameUser by rememberSaveable { mutableStateOf(selectedUser.nombre) }

    var textSurnameUser by rememberSaveable { mutableStateOf(selectedUser.apellido) }
    var textFnacUser by rememberSaveable { mutableStateOf(selectedUser.fnac) }
    var textEmailUser by rememberSaveable { mutableStateOf(selectedUser.email) }
    var textUserUser by rememberSaveable { mutableStateOf(selectedUser.user) }
    var textPasswordUser by rememberSaveable { mutableStateOf(selectedUser.passwrd) }
    var textRolUser by rememberSaveable { mutableStateOf(selectedUser.rol) }

    //

    Scaffold(

        scaffoldState = scaffoldState,

        //Preguntar como cojones hacemos el menú
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de empleado",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {


                    ///////////////
                    IconButton(
                        onClick = {
                            //Eliminar empleado
                            navController.navigate(Destinations.EmployeeManager.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar usaurio",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }

                    /////////////////
                    Box (Modifier.wrapContentSize()){
                        IconButton(onClick = {
                            expanded.value = true
                            result.value = "More icon clicked"
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More icon"
                            )
                        }

                        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                    navController.navigate(Destinations.EmployeeManager.route)

                                }) {
                                Text(text = "Eliminar empleado")
                            }
                            Divider()
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                }) {
                                Text(text = "Nuevo empleado")
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
                            selectedUser = Users(id,textDniUser, textNameUser, textSurnameUser, textFnacUser, textEmailUser, textUserUser, textPasswordUser, textRolUser)
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
        })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    IntermodularTheme {
        val navController = rememberNavController()
        MainEditEmployee(navController,"1")
    }
}
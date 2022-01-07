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
import sainero.dani.intermodular.Views.Administration.Employee.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.Destinations
import sainero.dani.intermodular.navigation.NavigationHost

@ExperimentalFoundationApi
class CreateEployee : ComponentActivity() {
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
fun MainCreateEmployee(navController: NavController) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }



    //Test

    var textDniUser by rememberSaveable { mutableStateOf("") }
    var textNameUser by rememberSaveable { mutableStateOf("") }

    var textSurnameUser by rememberSaveable { mutableStateOf("") }
    var textFnacUser by rememberSaveable { mutableStateOf("") }
    var textEmailUser by rememberSaveable { mutableStateOf("") }
    var textUserUser by rememberSaveable { mutableStateOf("") }
    var textPasswordUser by rememberSaveable { mutableStateOf("") }
    var textRolUser by rememberSaveable { mutableStateOf("") }

    //

    Scaffold(

        scaffoldState = scaffoldState,

        //Preguntar como cojones hacemos el menú
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Creación de un empleado",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {

                    /////////////////
                    Box (Modifier.wrapContentSize()){
                        IconButton(onClick = {
                            expanded.value = true

                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More icon Clicked"
                            )
                        }

                        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                    navController.navigate(Destinations.EmployeeManager.route)

                                }) {
                                Text(text = "Manejar todos los empleados")
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
                            textDniUser = ""
                            textNameUser = ""
                            textSurnameUser = ""
                            textFnacUser = ""
                            textEmailUser = ""
                            textUserUser = ""
                            textPasswordUser = ""
                            textRolUser = ""
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
                            //Subir en la BD con este objeto
                            val newUser = Users("id", textDniUser,textNameUser,textSurnameUser,textFnacUser,textUserUser,textPasswordUser,textEmailUser,textRolUser)
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
                            contentDescription = "Crear empleado",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Crear empleado", fontSize = 15.sp)
                    }

                }
            }
        })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview9() {
    sainero.dani.intermodular.Views.Administration.ui.theme.IntermodularTheme {
        val navController = rememberNavController()
        MainCreateEmployee(navController)
    }
}
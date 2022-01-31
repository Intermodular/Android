package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Controladores.ViewModelUsers
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Views.Administration.ui.theme.IntermodularTheme
import kotlin.concurrent.thread


@ExperimentalFoundationApi
class EditEmployee : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@Composable
fun MainEditEmployee(id: Int,viewModelUsers: ViewModelUsers) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }


    viewModelUsers.getUserById(id)
/*
    //Test

    var selectedUser: Users = Users(2,"","","","","","","","admin","")
    var allUsers: MutableList<Users> = mutableListOf()
    for(i in 1..20)  allUsers.add(Users(i,"49760882Z" ,"empleado" + i,"x","x","x","x","x","admin",""))

    //Esto es una busqueda en la BD
   for(i in allUsers)
       if(i._id.equals(id))
           selectedUser = i
*/
    
    var selectedUser = viewModelUsers.user

    var (textDniUser, onValueChangeDniUser) = rememberSaveable { mutableStateOf(selectedUser.dni) }
    var (textNameUser, onValueChangeNameUser) = rememberSaveable { mutableStateOf("") }
    var (textSurnameUser, onValueChangeSurnameUser) = rememberSaveable { mutableStateOf("") }
    var (textFnacUser, onValueChangeFnacUser) = rememberSaveable { mutableStateOf("") }
    var (textEmailUser, onValueChangeEmailUser) = rememberSaveable { mutableStateOf("") }
    var (textUserUser, onValueChangeUserUser) = rememberSaveable { mutableStateOf("") }
    var (textPasswordUser, onValueChangePasswordUser) = rememberSaveable { mutableStateOf("") }
    var (textRolUser, onValueChangeRolUser) = rememberSaveable { mutableStateOf("") }

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
                            GlobalVariables.navController.navigate(Destinations.EmployeeManager.route)
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
                                    GlobalVariables.navController.navigate(Destinations.EmployeeManager.route)

                                }) {
                                Text(text = "Eliminar empleado")
                            }
                            Divider()
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                }) {
                                Text(text = "Gestionar nominas")
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

                createRowList(text = "DNI", value = textDniUser, onValueChange = onValueChangeDniUser)
                createRowList(text = "Name", value = textNameUser, onValueChange = onValueChangeNameUser)
                createRowList(text = "Surname", value = textSurnameUser, onValueChange = onValueChangeSurnameUser)
                createRowList(text = "Fecha Nacimiento", value = textFnacUser, onValueChange = onValueChangeFnacUser)
                createRowList(text = "Email", value = textEmailUser, onValueChange = onValueChangeEmailUser)
                createRowList(text = "User", value = textUserUser, onValueChange = onValueChangeUserUser)
                createRowList(text = "Contraseña", value = textPasswordUser, onValueChange = onValueChangePasswordUser)
                createRowList(text = "Rol", value = textRolUser, onValueChange = onValueChangeRolUser)

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
                            selectedUser = Users(id,textDniUser, textNameUser, textSurnameUser, textFnacUser, "", textUserUser, textPasswordUser, textRolUser,textEmailUser)
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


@Composable
private fun createRowList(text: String, value: String, onValueChange: (String) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text) },
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    IntermodularTheme {
        //MainEditEmployee(1)
    }
}

